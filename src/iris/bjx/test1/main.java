package iris.bjx.test1;

import com.google.gson.Gson;
import com.mysql.cj.log.Log;
import iris.bjx.test1.BaiduTranslateUtils.TransApi;
import iris.bjx.test1.JsonBeans.BTJsonBean;
import iris.bjx.test1.JsonBeans.TransResuBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class main {

    private static final String APP_ID = "20210316000729564";
    private static final String SECURITY_KEY = "emfoQyJw6xUD1BxRrnJH";

    public static void main(String[] args){
        ArrayList arrayListEn = new ArrayList();
        ArrayList arrayListZh = new ArrayList();
        arrayListEn = getSentences();
        TransApi transApi =new TransApi(APP_ID,SECURITY_KEY);
        arrayListZh = getTranslateResultWithBaidu(arrayListEn,transApi);
        insertTranslateResult(arrayListZh);
    }

    /**
     * 从数据库获得sentences的List
     * @return
     */
    private static ArrayList<String> getSentences(){
        ArrayList<String> stringArrayList = new ArrayList<String>();
        DBUtils dbUtils = new DBUtils();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select sentence from BJX0316.test";
        String sentence;
        try {
            connection = dbUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                sentence = resultSet.getString("sentence");
                System.out.println(sentence);
                stringArrayList.add(sentence);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbUtils.close(resultSet);
        dbUtils.close(statement);
        dbUtils.close(connection);

        return stringArrayList;
    }

    /**
     * 将翻译结果插入数据库
     * @param resultList
     */
    private static void insertTranslateResult(ArrayList<String> resultList){
        DBUtils dbUtils = new DBUtils();
        Connection connection = dbUtils.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO BJX0316.translate(id,translate_result)"+"values("+"?,?"+")";
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < resultList.size(); i++) {
                preparedStatement.setInt(1,i+1);
                preparedStatement.setString(2,resultList.get(i));
                preparedStatement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dbUtils.close(preparedStatement);
        dbUtils.close(connection);
    }

    /**
     * 从百度翻译结果JSON中提取最终翻译结果dst
     * @param jsonData
     * @return
     */
    private static String fromJSONWithGSON(String jsonData){
        String dst;
        Gson gson = new Gson();
        BTJsonBean btJsonBean = gson.fromJson(jsonData,BTJsonBean.class);
        List<TransResuBean> resuBeanList = btJsonBean.getTrans_result();
        dst = resuBeanList.get(0).getDst();
        return dst;
    }

    /**
     * 将sentences通过百度翻译得到中文结果List
     * @param arrayListEn
     * @param baiduApi
     * @return
     */
    private static ArrayList<String> getTranslateResultWithBaidu(ArrayList<String> arrayListEn, TransApi baiduApi){
        ArrayList<String> arrayListZh = new ArrayList<>();
        for(String s:arrayListEn){
            arrayListZh.add(fromJSONWithGSON(baiduApi.getTransResult(s,"auto","zh")));
        }
        return arrayListZh;
    }

}
