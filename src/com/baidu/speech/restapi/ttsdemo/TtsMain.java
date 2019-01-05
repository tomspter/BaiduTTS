package com.baidu.speech.restapi.ttsdemo;

import com.baidu.speech.restapi.common.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TtsMain {

    public static void main(String[] args) throws SQLException, IOException, DemoException {

        //TODO
        String selectSQL = "SELECT * FROM user_sound";
        List<String> name = new ArrayList<>();
        List<Integer>id=new ArrayList<>();

        String search[] = {};

        ResultSet resultSet = DBUtil.executQuery(selectSQL, search);
        while (resultSet.next()) {
            name.add(resultSet.getString(2));
            id.add(resultSet.getInt(1));
        }

        for(int i=0;i<name.size();i++) {

            String text = name.get(i);

            TTSUtil.download(text);

            //TODO
            String updateSQL = "update user_sound set sound_name=? where id=?";
            String update[] = {MD5Util.MD5Encode(name.get(i),"utf8"), String.valueOf(id.get(i))};
            DBUtil.executUpdate(updateSQL, update);
        }
    }
}
