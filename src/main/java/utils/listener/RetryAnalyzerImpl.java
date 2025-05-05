package utils;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzerImpl implements IRetryAnalyzer {
    public static final Logger logger = Logger.getLogger(RetryAnalyzerImpl.class.getName());
    private int count = 0;
    private static final int maxTry = 2;

    @Override
    public boolean retry(ITestResult result) {
        logger.warn("Retry attempt: " + count + " for test: " + result.getName());
        if (count < maxTry) {
            count++;
            return true;
        }
        return false;
    }
}
