package htl_leonding.fiplyteam.fiply.data;

import android.test.AndroidTestCase;

/**
 * Created by Gerald on 12/02/2016.
 */
public class DatabaseStatisticsTest extends AndroidTestCase {

    StatisticRepository str;

    @Override
    protected void setUp() throws Exception {
        StatisticRepository.setContext(mContext);
        str = StatisticRepository.getInstance();
        str.reCreateUebungenTable();
    }


}
