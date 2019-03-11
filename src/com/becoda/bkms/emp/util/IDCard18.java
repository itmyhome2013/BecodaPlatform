//***************************************************
//	陶建林创建于2004年12月22日
//	最后修改于2004年12月22日
//	修改人：陶建林
//
//	用于对输入身份证进行校验并返回18位身份证号码
//
//	方法：public String Get18(String sInput)
//
//	
//***************************************************

package com.becoda.bkms.emp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDCard18 {
    public void IDCard18() {
    }

    int iLength = 0;
    String Num = "";
    String EndCh = "";
    String sTmp17 = "";
    String sTmp15 = "";
    String s18ID = "";

    public String Get18(String sInput) {

        if (ChkLength(sInput) != 0) s18ID = "lerr";
        else if (ChkStyle(sInput) != 0) s18ID = "serr";
        else if (ChkBirth() != 0) s18ID = "berr";
        else if (Mak18Id() != 0) s18ID = "merr";
        else if (iLength == 18) {
            sInput = Num + EndCh;
            if (!sInput.equals(s18ID)) {

                //s18ID="cerr";

            }

        }

        //System.out.println("sTmp17 = "+sTmp17);
        //System.out.println("sTmp15 = "+sTmp15);
        //System.out.println("Num = "+Num);
        //System.out.println("EndCh = "+EndCh);

        return s18ID;
    }

    /*================检查长度是否非法，为15位或18位===============*/
    private int ChkLength(String sInput) {
        iLength = sInput.length();
        if (iLength != 18 && iLength != 15) return 1;
        else return 0;
    }

/*======================检查格式是否非法=======================*/
/*=    若为18位则前17位为数字，最后一位为数字或x,X,e中之一    =*/
/*=    若为15位则应为数字                                     =*/

    /*=============================================================*/
    private int ChkStyle(String sInput) {
        double TestNum = 0.0;
        int TestInt = 0;
        String sFir3 = "";

        if (iLength == 18) {
            sFir3 = GetStrPart(sInput, 0, 2);
            if (sFir3.equals("000")) {
                iLength = 15;
                sInput = GetStrPart(sInput, 3, 17);
                Num = sInput;
                sTmp17 = GetStrPart(sInput, 0, 5) + "19" + GetStrPart(sInput, 6, 14);
                sTmp15 = sInput;
            } else {
                Num = GetStrPart(sInput, 0, 16);
                EndCh = GetStrPart(sInput, 17, 17);
                if (EndCh.equals("x")) EndCh = "X";
                try {
                    if (!EndCh.equals("X") && !EndCh.equals("e")) {
                        TestInt = Integer.parseInt(EndCh);
                    }
                } catch (Exception e) {
                    return 1;
                }
                sTmp17 = Num;
                sTmp15 = GetStrPart(sInput, 0, 5) + GetStrPart(sInput, 8, 16);
            }
        } else {
            Num = sInput;
            sTmp17 = GetStrPart(sInput, 0, 5) + "19" + GetStrPart(sInput, 6, 14);
            sTmp15 = sInput;
        }


        try {
            TestNum = Double.parseDouble(Num);
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    /*=======================检查生日是否非法=====================*/
    private int ChkBirth() {
        String sBirth = GetStrPart(sTmp17, 6, 13);
        String sChkB = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date dBirth = simpleDateFormat.parse(sBirth);
            sChkB = simpleDateFormat.format(dBirth);
//			System.out.println(sChkB);
            if (!sChkB.equals(sBirth)) return 1;
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    /*======================用于按位截取字符串====================*/
    private String GetStrPart(String sInput, int iHead, int iEnd) {
        int j = 0;
        int iLen = sInput.length();
        String sOutput = "";

        if (iLen < iEnd || iLen < iHead) return "err";
        else if (iEnd < 0) return "err";
        else if (iHead < 0) return "err";
        else if (iEnd < iHead) return "err";

        for (j = 0; j <= iLen; j++) {
            if (j >= iHead && j <= iEnd) {
                sOutput = sOutput + sInput.charAt(j);
            }
        }
        return sOutput;
    }

    /*================根据17位字串产生18位身份证号================*/
    private int Mak18Id() {
        String sPower = "0709100508040201060307091005080402";
        int i = 0;
        String MulId = "";
        String MulPw = "";
        int result = 0;

        for (i = 1; i <= 17; i++) {
            MulId = GetStrPart(sTmp17, i - 1, i - 1);
            MulPw = GetStrPart(sPower, i * 2 - 2, i * 2 - 1);
            result = result + Integer.parseInt(MulId) * Integer.parseInt(MulPw);
        }
//		System.out.println("result = "+result);

        result = result % 11;
        switch (result) {
            case 0:
                s18ID = sTmp17 + "1";
                break;
            case 1:
                s18ID = sTmp17 + "0";
                break;
            case 2:
                s18ID = sTmp17 + "X";
                break;
            case 3:
                s18ID = sTmp17 + "9";
                break;
            case 4:
                s18ID = sTmp17 + "8";
                break;
            case 5:
                s18ID = sTmp17 + "7";
                break;
            case 6:
                s18ID = sTmp17 + "6";
                break;
            case 7:
                s18ID = sTmp17 + "5";
                break;
            case 8:
                s18ID = sTmp17 + "4";
                break;
            case 9:
                s18ID = sTmp17 + "3";
                break;
            case 10:
                s18ID = sTmp17 + "2";
                break;
            default:
                s18ID = sTmp17 + "e";
                break;
        } /* end of switch */
        return 0;
    }
}   