package com.honap.madhumitra.utils;


import com.honap.madhumitra.entity.UserAccount;
import com.honap.madhumitra.entity.UserWeight;
import com.honap.madhumitra.model.MadhumitraModel;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Chetan S.
 */
public class Utils {

    private static long FIVE_MIN_SEC = 5 * 60;
    private static long ELEVEN_MIN_SEC = 11 * 60;
    private static long TWELVE_HOURS_SEC = 12 * 60 * 60;

    public static int getHour(String timeString) {
        return getTimeComponent(timeString, "hour");
    }

    public static int getMin(String timeString) {
        return getTimeComponent(timeString, "min");
    }

    private static int getTimeComponent(String timeString, String component) {
        int maxCharArrayIndex = timeString.length();
        int ret = 0;
        String amPm = timeString.substring(maxCharArrayIndex - 2, maxCharArrayIndex);
        String withoutAmPm = timeString.substring(0, (maxCharArrayIndex - 2));
        String[] hrMinArray = withoutAmPm.split(":");
        int hr = Integer.parseInt(hrMinArray[0]);
        int min = Integer.parseInt(hrMinArray[1]);
        if (component.equalsIgnoreCase("min")) {
            return min;
        } else {
            if (amPm.equalsIgnoreCase("am")) {
                return hr;
            } else {
                return hr + 12;
            }
        }
    }


    public static Date getDateTime(int day, int month, int year, int min, int hour) {
        Date date = new Date();
        date.setDate(day);
        date.setMonth(month);
        date.setYear(year);
        date.setMinutes(min);
        date.setHours(hour);
        date.setSeconds(0);
        return date;
    }

    public static boolean compareDates(Date date1, Date date2) {
        if ((date1.getDate() == date2.getDate()) && (date1.getMonth() == date2.getMonth()) && (date1.getYear() == date2.getYear())) {
            return true;
        } else
            return false;
    }

    public static boolean compareWeek(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        //int week1 = cal.get(Calendar.WEEK_OF_YEAR);
        int day1 = date1.getDate();
        int month1 = date1.getMonth();
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (compareMonth(date1, date2)) {
            if ((date2.getDate() - date1.getDate()) <= 7) {
                return true;
            } else  //current date is lesser than compared date
                return false;
        } else if ((date2.getMonth() - date1.getMonth() == 1))//last few days in prev month
        {
            int daysInDate1Month = daysOfMonth[date1.getMonth()];
            if ((date2.getDate() - 7) > 0)//current date is within first week of this month
            {
                if (date1.getDate() - (daysInDate1Month - (7 - date2.getDate())) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else
                return false;

        } else if ((date2.getMonth() == 1) && ((date2.getDate() - 7) > 0)) {
            int daysInDate1Month = daysOfMonth[date1.getMonth()];
            if (date1.getDate() - (daysInDate1Month - (7 - date2.getDate())) > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

        //cal.setTime(date2);
        //int week2 = cal.get(Calendar.WEEK_OF_YEAR);
        //if(week1 == week2)
        //    return true;
        //else
        //    return false;
    }

    public static boolean compareMonth(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int month1 = cal.get(Calendar.MONTH);
        cal.setTime(date2);
        int month2 = cal.get(Calendar.MONTH);
        if (month1 == month2)
            return true;
        else
            return false;
    }

    public static boolean inFiveMin(Date date) {
        // current date time
        Date currentDateTime = GregorianCalendar.getInstance().getTime();
//        boolean dateComparison = compareDates(currentDateTime,date);
        // compare time
        long diffSec = (date.getTime() - currentDateTime.getTime()) / 1000;
        if (diffSec < 0) return false;
        if (diffSec <= FIVE_MIN_SEC) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean inPriorFiveMin(Date date) {
        // current date time
        Date currentDateTime = GregorianCalendar.getInstance().getTime();
//        boolean dateComparison = compareDates(currentDateTime,date);
        // compare time
        long diffSec = (date.getTime() - currentDateTime.getTime()) / 1000;
        if (diffSec < 0)
            diffSec = diffSec * -1;
        else
            return false;
        if (diffSec <= FIVE_MIN_SEC) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean inPriorElevenMin(Date date) {
        // current date time
        Date currentDateTime = GregorianCalendar.getInstance().getTime();
//        boolean dateComparison = compareDates(currentDateTime,date);
        // compare time
        long diffSec = (date.getTime() - currentDateTime.getTime()) / 1000;
        if (diffSec < 0)
            diffSec = diffSec * -1;
        else
            return false;
        if (diffSec <= ELEVEN_MIN_SEC) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean withinTwelveHours(Date date) {
        // current date time
        Date currentDateTime = GregorianCalendar.getInstance().getTime();
        //boolean dateComparison = compareDates(currentDateTime,date);
        // compare time
        long diffSec = (date.getTime() - currentDateTime.getTime()) / 1000;
        if (diffSec < 0) return false;
        if (diffSec <= TWELVE_HOURS_SEC) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean withinTwelveHours(Date start, Date subject) {
        //boolean dateComparison = compareDates(currentDateTime,date);
        // compare time
        long diffSec = (subject.getTime() - start.getTime()) / 1000;
        if (diffSec < 0) return false;
        if (diffSec <= TWELVE_HOURS_SEC) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNormalizedCalories(int cal) {
        // get latest user weight
        UserWeight userWeight = (UserWeight) MadhumitraModel.getInstance().getCurrentUserAccount().getWeightTrail().toArray()
                [MadhumitraModel.getInstance().getCurrentUserAccount().getWeightTrail().size() - 1];

        int c = (userWeight.getWeight() * cal) / 70;
        return c;
    }

    public static String getDateTimeString(Date date) {
        return new SimpleDateFormat("h:mm a, d MMM yyyy").format(date);
    }

    public static String getBtnDateString(Date date) {
        return new SimpleDateFormat("MMM d, ''yy").format(date);
    }

    public static double getUserCalorieReq() {
        UserAccount userAccount = MadhumitraModel.getInstance().getCurrentUserAccount();
        int age = userAccount.getAge();
        int height = userAccount.getHeightCm();
        int weight = (Integer) userAccount.getWeightTrail().toArray()[userAccount.getWeightTrail().size() - 1];

        double bmr = Double.MIN_VALUE;
        double calReq = Double.MIN_VALUE;

        if (userAccount.getSex().equalsIgnoreCase("male")) {
            bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        }

        if (userAccount.getSex().equalsIgnoreCase("female")) {
            bmr = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }

        String lifestyle = userAccount.getLifestyle();

        if (lifestyle.equals("Sedentary (No exercise)")) {
            calReq = bmr * 1.2;
        }

        if (lifestyle.equals("Lightly Active (Exercise: 1-3 days/week)")) {
            calReq = bmr * 1.375;
        }

        if (lifestyle.equals("Moderately Active (Exercise: 3-5 days/week)")) {
            calReq = bmr * 1.55;
        }

        if (lifestyle.equals("Active (Exercise: 6-7 days/week)")) {
            calReq = bmr * 1.725;
        }

        if (lifestyle.equals("Very Active (Heavy exercise/phy. job)")) {
            calReq = bmr * 1.9;

        }
        return calReq;
    }

}
