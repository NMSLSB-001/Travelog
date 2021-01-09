package com.example.travelog.ui.DiscoverFragment.View;

public class StringData {
    private static String jsonStr = getData();
    private static String getData() {
        // 模拟网络获取数据
        String json = "{list:["
                + "{\"id\":111,\"headImg\":\"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4059021347,2703226688&fm=23&gp=0.jpg\",\"userName\":\"User2\",\"location\":\"BeiJing\",\"title\":\"NMSL\",\"content\":\"test ....\",\"time\":\"2021/1/6 20:46\","
                + "  \"urls\":[" + "\"https://firebasestorage.googleapis.com/v0/b/firetest2-abd4d.appspot.com/o/photo%2F422e7f56-0d7a-4972-b6f5-774578a97d62.png?alt=media&token=a6780a5c-00d1-4b75-a652-314566851d4d\","
                + "\"https://firebasestorage.googleapis.com/v0/b/firetest2-abd4d.appspot.com/o/photo%2Ff32b8b71-f785-4eb6-bc69-998bd78c7513.png?alt=media&token=6430ca94-1dca-4ede-bf17-d793b0694c9d\","
                + "\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486458146984&di=7374bde21e5176784fa010ac8a6984eb&imgtype=0&src=http%3A%2F%2Fimg1.cache.netease.com%2Fhouse%2F2015%2F6%2F18%2F20150618101320f94b8.jpg\","
                + "\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486458146984&di=7374bde21e5176784fa010ac8a6984eb&imgtype=0&src=http%3A%2F%2Fimg1.cache.netease.com%2Fhouse%2F2015%2F6%2F18%2F20150618101320f94b8.jpg\","
                + "\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486458146984&di=7374bde21e5176784fa010ac8a6984eb&imgtype=0&src=http%3A%2F%2Fimg1.cache.netease.com%2Fhouse%2F2015%2F6%2F18%2F20150618101320f94b8.jpg\","
                + "\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486458146984&di=7374bde21e5176784fa010ac8a6984eb&imgtype=0&src=http%3A%2F%2Fimg1.cache.netease.com%2Fhouse%2F2015%2F6%2F18%2F20150618101320f94b8.jpg\","
                + "\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486458146984&di=7374bde21e5176784fa010ac8a6984eb&imgtype=0&src=http%3A%2F%2Fimg1.cache.netease.com%2Fhouse%2F2015%2F6%2F18%2F20150618101320f94b8.jpg\","
                + "\"https://firebasestorage.googleapis.com/v0/b/firetest2-abd4d.appspot.com/o/photo%2F7ef9f372-f6c0-4dd7-9544-9f3c46a5a651.png?alt=media&token=cdcd68cd-1815-4f76-87a1-6e2125412990\","
                + "\"https://firebasestorage.googleapis.com/v0/b/firetest2-abd4d.appspot.com/o/photo%2Ff32b8b71-f785-4eb6-bc69-998bd78c7513.png?alt=media&token=6430ca94-1dca-4ede-bf17-d793b0694c9d\"]}]}";

        return json;
    }

    public static void setJsonStr(String A) {
        com.example.travelog.ui.DiscoverFragment.View.StringData.jsonStr = A;
    }
    public static String getJsonStr() {
        return jsonStr;
    }
    public static String getUserName() {
        return "NMSL";
    }
}
