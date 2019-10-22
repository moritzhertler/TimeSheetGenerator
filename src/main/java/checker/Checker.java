package checker;

import data.Entry;
import data.FullDocumentation;
import data.TimeSpan;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Calendar;

/**
 * @author Liam Wachter
 */
public class Checker {
    private static final TimeSpan EARLIEST = new TimeSpan(6, 0);
    private static final TimeSpan LATEST = new TimeSpan(22, 0);

    /**
     * Perform the actual checking.
     *
     * @param toCheck object to check
     * @return error message that may be outputted to the user. Or an empty String if no error was found
     */
    public String check(FullDocumentation toCheck) {
        String errorMessage;
        if (!(errorMessage = checkSum(toCheck)).equals(ErrorMessages.none))
            return errorMessage;
        if (!(errorMessage = nameNotEmpty(toCheck)).equals(ErrorMessages.none))
            return errorMessage;
        if (!(errorMessage = checkSundays(toCheck)).equals(ErrorMessages.none))
            return errorMessage;


        return ErrorMessages.none;
    }
    /**
     * Check if sum is correct and less or equal max working hours per month
     */
    private String checkSum(FullDocumentation toCheck) {
        TimeSpan sum = new TimeSpan(0, 0);
        Arrays.stream(toCheck.getEntries()).forEach(e -> sum.add(e.getWorkingTime()));
        // worked more than max work hours
        if (sum.compareTo(toCheck.getMaxWorkTime()) > 0)
            return ErrorMessages.workedToMuch;
        return ErrorMessages.none;
    }

    /**
     * There should be at least some text in the institution field
     */
    private String nameNotEmpty(FullDocumentation toCheck)  {
        return toCheck.getDepartmentName().equals("") ? ErrorMessages.nameMissing : ErrorMessages.none;
    }

    /**
     * looks at all work done at one day and checks if enough break was taken
     */
    private String checkDay(FullDocumentation toCheck) {
        throw new NotImplementedException();
    }

    /**
     * checks if work was done outside allowed working hours.
     */
    private String checkTime(FullDocumentation toCheck) {
        throw new NotImplementedException();
    }

    private String checkSundays(FullDocumentation toCheck) {
        Calendar calendar = Calendar.getInstance();
        for (Entry e : toCheck.getEntries()) {
            calendar.setTime(e.getDate());
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SUNDAY)
                return ErrorMessages.sunday;
        }
        return ErrorMessages.none;
    }
}
