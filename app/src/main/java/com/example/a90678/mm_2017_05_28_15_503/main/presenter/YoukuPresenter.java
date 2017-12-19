package com.example.a90678.mm_2017_05_28_15_503.main.presenter;

import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseListStatus;
import com.example.a90678.lkx_common_17_05_17_16_45.common.status.BaseLoadingStatus;
import com.example.a90678.mm_2017_05_28_15_503.main.data.MainFrgAdvResult;

/**
 * Created by 90678 on 2017/6/10.
 */

public class YoukuPresenter extends MainFrgAdvPresenter {

    public YoukuPresenter(BaseListStatus<MainFrgAdvResult.MainFrgAdv> baseListStatus, BaseLoadingStatus<MainFrgAdvResult> baseLoadingStatus) {
        super(baseListStatus, baseLoadingStatus);
    }

    @Override
    public void loadData() {
//        super.loadData();
        obserVable();
    }

    @Override
    public void loadMore(MainFrgAdvResult.MainFrgAdv data) {
//        super.loadMore(data);
        obserVable();
    }

    private void obserVable () {
////                baseLoadingObv(obv);
////        Observable<MainFrgAdvResult> obv = MainFrgAdvModule.getMainFrgAdvService().youkuList(
////                "", page, 20);
//        Observable<MainFrgAdvResult> obv = Observable.create(
//                new Observable.OnSubscribe<MainFrgAdvResult>() {
//                    @Override
//                    public void call(Subscriber<? super MainFrgAdvResult> subscriber) {
//                        getUrl(subscriber);
//                    }
//                }
//        );
//
//        Subscriber<MainFrgAdvResult> mySubscriber = new Subscriber<MainFrgAdvResult >() {
//            @Override
//            public void onNext(MainFrgAdvResult mainFrgAdvResult) {
//                success(mainFrgAdvResult);
//            }
//
//            @Override
//            public void onCompleted() {
//                complete();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                error(e.getMessage());
//            }
//        };
//
//        obv.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mySubscriber);
//
//    }
//
//    public void getUrl (Subscriber<? super MainFrgAdvResult> subscriber) {
////        page = page-1;
////        for (int i = 0 ; i < 30 ; i ++) {
//            String urlString = "http://list.youku.com/category/show/c_96_s_1_d_1_p_"
//                    +(page+1)+".html?spm=0.0.0.0.0yEjiS";
//        Log.e("访问地址","访问地址 " + urlString );
//            Element element;
//            try {
//                element = Jsoup.connect(urlString).referrer("http://movie.youku.com/?spm="
//                        + "0.0.topNav.5~1~3!3~A.KPtZd5").timeout(30000).get();
////				System.out.println("element "+element);
//                if (null == element) {
//                    return;
//                }
//                List<MainFrgAdvResult.MainFrgAdv> mainFrgAdvList = new ArrayList<>();
//                Elements ping = element.select(".box-series .panel .pack-film");
//                for (int j = 0 ; j < ping.size(); j ++) {
//                    String name = ping.get(j).select(".title a").text();
//                    String coStars = ping.get(j).select(".actor a").text();
//                    String img = ping.get(j).select(".quic").attr("src");
//                    String number = ping.get(j).select(".info-list li").last().text();
//                    String html = ping.get(j).select(".title a").attr("href");
//                    IVideoInfo iVideoInfo = VideoParser.getInstance().parse("http:"+html);
//                    String videoUrl = iVideoInfo.provideSource(HD_UNSPECIFIED);
//                    Log.e(" html "," html http:" + html);
////                    Log.e("得到"," 得到 " + ping.get(j).toString());
////                    System.out.println("name " + name);
////                    System.out.println("coStars " + coStars);
////                    System.out.println("img " + img);
////                    System.out.println("number   " + number);
//
//                    MainFrgAdvResult.MainFrgAdv mainFrgAdv = new MainFrgAdvResult.MainFrgAdv();
//                    mainFrgAdv.setTitle(name);
//                    mainFrgAdv.setUrlImg(img);
//                    mainFrgAdv.setNumber(number);
//                    mainFrgAdv.setUrlVideo(videoUrl);
//
//                    mainFrgAdvList.add(mainFrgAdv);
//
//                }
//                MainFrgAdvResult mainFrgAdvResult = new MainFrgAdvResult();
//                mainFrgAdvResult.setResult(mainFrgAdvList);
//                subscriber.onNext(mainFrgAdvResult);
//
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                System.out.println("element +error ");
//                subscriber.onError(e);
//            }
//            subscriber.onCompleted();
////        }
    }
}
