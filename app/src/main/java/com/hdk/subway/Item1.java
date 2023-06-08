package com.hdk.subway;

public class Item1 {

    //도착지 방면
    String trainLineNm;
    //열차종류 (급행,ITX,일반)
    String btrainSttus;
    //열차 도착정보를 생성한 시각
    String recptnDt;
    //도착 메세지
    String arvlMsg2;
    //막차
    String last;

    public Item1(String trainLineNm, String btrainSttus, String last, String arvlMsg2, String recptnDt){

        this.trainLineNm = trainLineNm;
        this.btrainSttus = btrainSttus;
        this.last = last;
        this.arvlMsg2 = arvlMsg2;
        this.recptnDt = recptnDt;
    }

    public Item1(){

    }

}

