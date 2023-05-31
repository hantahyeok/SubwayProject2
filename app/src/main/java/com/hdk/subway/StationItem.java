package com.hdk.subway;

public class StationItem {

    //지하철 호선 ID
    private String subwayId;
    //도착지 방면
    private String trainLineNm;
    //지하철 역명
    private String statnNm;
    //열차종류
    private String btrainSttus;
    //열차 도착에정 시간
    private String barvlDt;
    //종착 지하철 역명
    private String bstatnNm;
    //열차 도착정보를 생성한 시각
    private String recptnDt;
    //첫번째 도착 메세지
    private String arvlMsg2;
    //두번째 도착 메세지
    private String arvlMsg3;
    //도착 코드
    private String arvlCd;

    public String getsubwayId(){return subwayId;}
    public String gettrainLineNm(){return trainLineNm;}
    public String getstatnNm(){return statnNm;}
    public String getbtrainSttus(){return btrainSttus;}
    public String getbarvlDt(){return barvlDt;}
    public String getbstatnNm(){return bstatnNm;}
    public String getrecptnDt(){return recptnDt;}
    public String getarvlMsg2(){return arvlMsg2;}
    public String getarvlMsg3(){return arvlMsg3;}
    public String getarvlCd(){return arvlCd;}

    public void setsubwayId(String subwayId){this.subwayId = subwayId;}
    public void settrainLineNm(String trainLineNm){this.trainLineNm = trainLineNm;}
    public void setstatnNm(String statnNm){this.statnNm = statnNm;}
    public void setbtrainSttus(String btrainSttus){this.btrainSttus = btrainSttus;}
    public void setbarvlDt(String barvlDt){this.barvlDt = barvlDt;}
    public void setbstatnNm(String bstatnNm){this.bstatnNm = bstatnNm;}
    public void setrecptnDt(String recptnDt){this.recptnDt = recptnDt;}
    public void setarvlMsg2(String arvlMsg2){this.arvlMsg2 = arvlMsg2;}
    public void setarvlMsg3(String arvlMsg3){this.arvlMsg3 = arvlMsg3;}
    public void setarvlCd(String arvlCd){this.arvlCd = arvlCd;}



}
