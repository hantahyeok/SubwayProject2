package com.hdk.subway;

public class Item1 {

    //지하철 호선 ID
    String subwayId;
    //도착지 방면
    String trainLineNm;
    //지하철 역명
    String statnNm;
    //열차종류
    String btrainSttus;
    //열차 도착에정 시간
    String barvlDt;
    //종착 지하철 역명
    String bstatnNm;
    //열차 도착정보를 생성한 시각
    String recptnDt;
    //첫번째 도착 메세지
    String arvlMsg2;
    //두번째 도착 메세지
    String arvlMsg3;
    //도착 코드
    String arvlCd;
    //연계호선
    String subwayList;

    public Item1(String trainLineNm, String statnNm, String btrainSttus, String barvlDt, String bstatnNm, String recptnDt, String arvlMsg2, String arvlMsg3, String arvlCd, String subwayList){
        this.trainLineNm = trainLineNm;
        this.statnNm = statnNm;
        this.btrainSttus = btrainSttus;
        this.barvlDt = barvlDt;
        this.bstatnNm = bstatnNm;
        this.recptnDt = recptnDt;
        this.arvlMsg2 = arvlMsg2;
        this.arvlMsg3 = arvlMsg3;
        this.arvlCd = arvlCd;
        this.subwayList = subwayList
    }

    public Item1(){

    }

}

