public class DateYMD {

    int month;
    int year;
    int day;

    @Override
    public String toString() {
        return String.format("Data: %02d / %02d / %04d", day, month, year);
    }

    public DateYMD(int day, int month, int year) {
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setData(int day, int month, int year) {
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public static boolean validMonth(int month) {

        if (month < 1 && month > 12) {
            return false;
        }
        return true;

    }

    public static boolean validYear(int year) {

        if (year < 1) {
            return false;
        }
        return true;

    }

    public static int monthDays(int month, int year) {

        int[] days = { 31, 28, 30 };

        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12:
                return days[0];
            case 2:
                if (leapYear(year) == true) {

                    return days[1] = 29;
                } else {
                    return days[1];
                }
            case 4, 6, 9, 11:
                return days[2];
            default:
                return 0;
        }

    }

    public static boolean leapYear(int year) {

        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }
        return false;

    }

    public static boolean valid(int day, int month, int year) {

        if (validMonth(month) == true && validYear(year) == true && day < monthDays(month, year) == true) {
            return true;
        }
        return false;

    }

    public void increment(int days) {

        if (day == monthDays(month, year)) {

            day = 0;

            if (month == 12) {
                month = 1;
                year++;
            } else {
                month++;
            }

        }

        day++;

    }

    public void decrement() {

        if (day == 1) {

            if (month == 1) {
                month = 12;
                year--;
            } else {
                month--;
            }

            day = monthDays(month, year) + 1;

        }
        day--;
    }

}
