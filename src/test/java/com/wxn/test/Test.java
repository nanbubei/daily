package com.wxn.test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String pname = "复旦大学831西方政治思想史2013年考研试题";
        getByPname(pname);

    }
    private static Map<String, String> schools = null;

    private static Map<String, Integer> colleges = null;

    private static Map<String, Integer> subjects = null;

    private static Map<Integer, String> provinces = null;


    static {
        try {
            schools = new LinkedHashMap<String, String>();
            colleges = new LinkedHashMap<String, Integer>();
            subjects = new LinkedHashMap<String, Integer>();
            provinces = new LinkedHashMap<Integer, String>();


//            List<Map<String, Object>> rs = Db.executeQuery("select * from kyzq_classily where pid in (select id from kyzq_classily where pid=0)");
//            for(int i = 0; i < rs.size(); i ++) {
//                schools.put(rs.get(i).get("name").toString(), rs.get(i).get("id")+"-"+rs.get(i).get("pid"));
//
//            }
//
//            rs = Db.executeQuery("select * from kyzq_classily where pid in (select id from kyzq_classily where pid in (select id from kyzq_classily where pid=0))");
//            for(int i = 0; i < rs.size(); i ++) {
//                colleges.put(rs.get(i).get("name").toString(), (Integer) rs.get(i).get("id"));
//            }
//
//            rs = Db.executeQuery("select * from kyzq_classily where pid in (select id from kyzq_classily where pid in (select id from kyzq_classily where pid in (select id from kyzq_classily where pid=0)))");
//            for(int i = 0; i < rs.size(); i ++) {
//                subjects.put(rs.get(i).get("name").toString().replaceAll(" ", ""), (Integer) rs.get(i).get("id"));
//            }
//
//            rs = Db.executeQuery("select * from kyzq_classily where pid=0");
//            for(int i = 0; i < rs.size(); i ++) {
//                provinces.put((Integer) rs.get(i).get("id"), rs.get(i).get("name").toString());
//            }

        } catch (Exception e) { }
    }

    public static KyzqBbs getByPname(String pname) {
        KyzqBbs bean = new KyzqBbs();
        if(pname == null)
            return null;
        /*
         * 根据'大学' 切割pname
         * 南京农业大学资源与环境科学学院810土壤学2004-2012、2014年考研真题汇编
         * 0是南京农业, 1是资源与环境科学学院810土壤学2004-2012、2014年考研真题汇编
         */
        String[] names = pname.split("大学");
        if(names.length < 2) {
            System.out.println("没有学校");
            return null;
        }

        //1.拿到学校名字和id
        String schoolName = names[0] + "大学";
        String schoolMessage = schools.get(schoolName);
        if(schoolMessage == null) {
            System.out.println("找不到" + schoolName);
            return null;
        }
        Integer schoolId = Integer.parseInt(schoolMessage.split("-")[0]);
        Integer schoolPid = Integer.parseInt(schoolMessage.split("-")[1]);
        pname = pname.replace(schoolName, "");

        //2.拿到系名字和id(从'大学'之后 到第一个数字之前是系)
        Matcher matcher = Pattern.compile("[0-9]").matcher(names[1]);
        if(! matcher.find()) {
            System.out.println("没有系");
            return null;
        }
        String collegeName = names[1].substring(0, matcher.start());
        if(collegeName == "") {
            System.out.println("没有系");
            return null;
        }
        Integer collegeId = colleges.get(collegeName);
        if(collegeId == null) {
            System.out.println("没有系");
            return null;
        }
        pname = pname.replace(collegeName, "");


        //3拿到专业
        matcher = Pattern.compile("[0-9]+[0-9]+[0-9]+[0-9]+").matcher(pname);

        if(! matcher.find()) {
            System.out.println("没有专业");
            return null;
        }
        String subjectName = pname.substring(0, matcher.start());
        Integer subjectId = subjects.get(subjectName);
        if(subjectId == null) {
            System.out.println("没有专业");
            return null;
        }
        pname = pname.replaceAll(subjectName, "");


        //4.isReal 1:真题 2:模拟题
        Integer isReal = -1;
        if(pname.contains("真题")) {
            isReal = 1;
        } else if (pname.contains("模拟试题")) {
            isReal = 2;
        }

        bean.isReal = isReal;
        bean.provinceId = schoolPid;
        bean.schoolId = schoolId;
        bean.collegeId = collegeId;
        bean.subjectId = subjectId;
        return bean;
    }

    static class KyzqBbs{
        public int provinceId;
        public int schoolId;
        public int collegeId;
        public int subjectId;
        public int isReal;
    }


//    public static void main(String[] args) {
//        int nums[] = new int[]{7,9,0,3,2,1};
////        InsertionSort.sort(nums);
////        BubbleSort.sort(nums);
//        MergeSort.sort(nums);
//        System.out.println(Arrays.toString(nums));
//    }

}
