package com.hdk.subway;

public class Item1 {

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
    //막차
    String last;

    public Item1(String trainLineNm, String btrainSttus, String last){
        this.trainLineNm = trainLineNm;
        this.btrainSttus = btrainSttus;
        this.last = last;
    }

    public Item1(){

    }

}

