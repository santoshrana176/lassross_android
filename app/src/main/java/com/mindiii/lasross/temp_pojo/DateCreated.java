package com.mindiii.lasross.temp_pojo;

public class DateCreated {
    private String date;
    private String timezone;
    private int timezoneType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(int timezoneType) {
        this.timezoneType = timezoneType;
    }

    @Override
    public String toString() {
        return
                "DateCreated{" +
                        "date = '" + date + '\'' +
                        ",timezone = '" + timezone + '\'' +
                        ",timezone_type = '" + timezoneType + '\'' +
                        "}";
    }
}
