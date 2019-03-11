package com.becoda.bkms.csu.test.pojo1;

import java.math.BigDecimal;


/**
 * KpClicks entity. @author MyEclipse Persistence Tools
 */

public class KpClicks  implements java.io.Serializable {


    // Fields    

     private String id;
     private String foreignId;
     private BigDecimal participagteNum;
     private BigDecimal clickNum;
     private String type;


    // Constructors

    /** default constructor */
    public KpClicks() {
    }

    
    /** full constructor */
    public KpClicks(String foreignId, BigDecimal participagteNum, BigDecimal clickNum, String type) {
        this.foreignId = foreignId;
        this.participagteNum = participagteNum;
        this.clickNum = clickNum;
        this.type = type;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public BigDecimal getParticipagteNum() {
        return this.participagteNum;
    }
    
    public void setParticipagteNum(BigDecimal participagteNum) {
        this.participagteNum = participagteNum;
    }

    public BigDecimal getClickNum() {
        return this.clickNum;
    }
    
    public void setClickNum(BigDecimal clickNum) {
        this.clickNum = clickNum;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
   








}