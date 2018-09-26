package com.duboscq.nicolas.mynews;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIStreams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Nicolas DUBOSCQ on 21/09/2018
 */

@RunWith(AndroidJUnit4.class)
public class APINewYorkTimesTest {

    @Test
    public void topStoriesAPITest() throws Exception {
        //1 - Get the stream
        Observable<GeneralInfo> generalInfo = APIStreams.getTopstoriesArticles();
        //2 - Create a new TestObserver
        TestObserver<GeneralInfo> testObserver = new TestObserver<>();
        //3 - Launch observable
        generalInfo.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        GeneralInfo docs = testObserver.values().get(0);
        assertThat("Copyright (c) 2018 The New York Times Company. All Rights Reserved.", docs.getCopyright().equals("Copyright (c) 2018 The New York Times Company. All Rights Reserved.") );
    }

    @Test
    public void mostPopularAPITest() throws Exception {
        //1 - Get the stream
        Observable<GeneralInfo> generalInfo = APIStreams.getMostPopularArticles();
        //2 - Create a new TestObserver
        TestObserver<GeneralInfo> testObserver = new TestObserver<>();
        //3 - Launch observable
        generalInfo.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        GeneralInfo docs = testObserver.values().get(0);
        assertThat("Copyright (c) 2018 The New York Times Company.  All Rights Reserved.", docs.getCopyright().equals("Copyright (c) 2018 The New York Times Company.  All Rights Reserved.") );
    }

    @Test
    public void weeklyAPITest() throws Exception {
        //1 - Get the stream
        Observable<GeneralInfo> generalInfo = APIStreams.getWeeklyArticles("sports");
        //2 - Create a new TestObserver
        TestObserver<GeneralInfo> testObserver = new TestObserver<>();
        //3 - Launch observable
        generalInfo.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        GeneralInfo docs = testObserver.values().get(0);
        assertThat("Copyright (c) 2018 The New York Times Company. All Rights Reserved.", docs.getCopyright().equals("Copyright (c) 2018 The New York Times Company. All Rights Reserved.") );
    }

    @Test
    public void searchAPITest() throws Exception {
        //1 - Get the stream
        Observable<GeneralInfo> generalInfo = APIStreams.getSearchDocsWithoutDate("\"trump\""+" AND section_name.contains:(\"sports\")");
        //2 - Create a new TestObserver
        TestObserver<GeneralInfo> testObserver = new TestObserver<>();
        //3 - Launch observable
        generalInfo.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        GeneralInfo docs = testObserver.values().get(0);
        assertThat("Copyright (c) 2018 The New York Times Company. All Rights Reserved.", docs.getCopyright().equals("Copyright (c) 2018 The New York Times Company. All Rights Reserved."));
    }
}
