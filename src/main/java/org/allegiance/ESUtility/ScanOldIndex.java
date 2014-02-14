package org.allegiance.ESUtility;


import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;

/**
 * User:    Abhishek
 * Date:    1/7/14
 * Time:    3:04 PM
 * Project: ESUtility
 */
public class ScanOldIndex {

    public static void Scan(Settings settings) throws IOException {
        //QueryBuilder qb = termQuery("reportid","69604634-70a0-4f25-b59e-e39a4effe595");   // Search Query
        SearchResponse scrollResp = settings.client.prepareSearch(settings.oldIndex.equals("") ? settings.index : settings.oldIndex)
                .setSearchType(SearchType.SCAN)
                .setScroll(new TimeValue(60000))
               // .setQuery(qb)
                .setSize(settings.scrollSize).execute().actionGet(); //100 hits per shard will be returned for each scroll
        //Scroll until no hits are returned
        settings.documentsCount = scrollResp.getHits().getTotalHits() ;
        System.out.println(settings.documentsCount + " documents found in the " + (settings.oldIndex.equals("") ? settings.index : settings.oldIndex));
        while (true) {
            scrollResp = settings.client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
              System.out.println("Reading 100 documents");
            for (SearchHit hit : scrollResp.getHits()) {
                if(settings.operationType == Settings.OpType.BACKUP)
                {
                    Backup.BackupIndex(hit);
                }
                else if(settings.operationType == Settings.OpType.MODIFYINDEX)
                {
                    ChangeMapping.ChangeMapping(hit, settings);//Handle the hit...
                }
            }
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }
    }
}
