/**
 * 
 */
package testPack;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author velan
 *
 */
public class Test {

  public int totalComparisons = 0;

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Test object = new Test();
    ArrayList<String> randomAccessBufferDatum = new ArrayList<String>();
    ArrayList<String> maxValueDatum = new ArrayList<String>();
    ArrayList<String> minValueDatum = new ArrayList<String>();
    String oddValueData = new String();
    String minValueData = new String();
    String maxValueData = new String();
    oddValueData = null;
    randomAccessBufferDatum = object.readInputDatum();
    Iterator<String> datumIterator = randomAccessBufferDatum.iterator();
    if ((randomAccessBufferDatum.size()) % 2 != 0 && datumIterator.hasNext()) {
      oddValueData = datumIterator.next();// If odd first value is removed and stored separately
    }
    while (datumIterator.hasNext()) {
      Float commonTemperatureValue1 = (float) 0, commonTemperatureValue2 = (float) 0;
      String value1 = datumIterator.next();
      String value2 = datumIterator.next();
      commonTemperatureValue1 =
          object.tempConverter(object.getTemperatureValue(value1), object.getUnitOfMeasure(value1));
      commonTemperatureValue2 =
          object.tempConverter(object.getTemperatureValue(value2), object.getUnitOfMeasure(value2));
      object.totalComparisons += 1;
      if (commonTemperatureValue1 >= commonTemperatureValue2) {
        maxValueDatum.add(value1);
        minValueDatum.add(value2);
      } else {
        maxValueDatum.add(value2);
        minValueDatum.add(value1);
      }

    }
    if (!minValueDatum.isEmpty()) {
      minValueData = object.findMinimumValue(minValueDatum, object);
    } else if (oddValueData != null) {
      minValueData = oddValueData;
    } else {
      minValueData = "No inputs present to find minimum value";
    }

    if (!maxValueDatum.isEmpty()) {
      maxValueData = object.findMaximumValue(maxValueDatum, object);
    } else if (oddValueData != null) {
      maxValueData = oddValueData;
    } else {
      maxValueData = "No inputs present to find minimum value";
    }

    if (oddValueData != null) {
      object.totalComparisons += 1;
      if (object.tempConverter(object.getTemperatureValue(minValueData),
          object.getUnitOfMeasure(minValueData)) >= object.tempConverter(
              object.getTemperatureValue(oddValueData), object.getUnitOfMeasure(oddValueData))) {
        minValueData = oddValueData;
      }
      object.totalComparisons += 1;
      if (object.tempConverter(object.getTemperatureValue(maxValueData),
          object.getUnitOfMeasure(maxValueData)) < object.tempConverter(
              object.getTemperatureValue(oddValueData), object.getUnitOfMeasure(oddValueData))) {
        maxValueData = oddValueData;
      }

    }
    System.out.println("Total Number of comparisons used is " + object.totalComparisons);
    System.out.println("Please Enter Root or Not (1 or 0)");
    Scanner rootOrNotScanner = new Scanner(System.in);
    switch (rootOrNotScanner.nextInt()) {
      case 0: {
        object.writeOutputDatum(minValueData, maxValueData);
        System.out.println(
            "The output values are successfully written on the file InputToParentNode.txt and sent to the parent Node ");
      }
      case 1: {
        System.out.println("Minimum value datum is " + minValueData);
        System.out.println("Maximum value datum is " + maxValueData);
        break;
      }
      default: {
        System.out.println("Not a Valid resonse");
        break;
      }
    }
  }

  String findMinimumValue(ArrayList<String> minimumList, Test object) {
    String minimumValue = null;
    Iterator<String> minValueIterator = minimumList.iterator();
    if (minValueIterator.hasNext()) {
      minimumValue = minValueIterator.next();
    }
    while (minValueIterator.hasNext()) {
      String value = minValueIterator.next();
      object.totalComparisons += 1;
      if (object.tempConverter(object.getTemperatureValue(minimumValue),
          object.getUnitOfMeasure(minimumValue)) >= object
              .tempConverter(object.getTemperatureValue(value), object.getUnitOfMeasure(value))) {
        minimumValue = value;
      }
    }
    return minimumValue;

  }

  String findMaximumValue(ArrayList<String> maximumList, Test object) {
    String maximumValue = null;
    Iterator<String> maxValueIterator = maximumList.iterator();
    if (maxValueIterator.hasNext()) {
      maximumValue = maxValueIterator.next();
    }
    while (maxValueIterator.hasNext()) {
      String value = maxValueIterator.next();
      object.totalComparisons += 1;
      if (object.tempConverter(object.getTemperatureValue(maximumValue),
          object.getUnitOfMeasure(maximumValue)) <= object
              .tempConverter(object.getTemperatureValue(value), object.getUnitOfMeasure(value))) {
        maximumValue = value;
      }
    }
    return maximumValue;

  }

  ArrayList<String> readInputDatum() {
    ArrayList<String> randomAccessBufferDatum = new ArrayList<String>();
    try {
      FileInputStream fstreamInput = new FileInputStream("InputToCurrentNode.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(fstreamInput));
      String strLine;
      while ((strLine = br.readLine()) != null) {
        randomAccessBufferDatum.add(strLine);
      }
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("Error in Reading file");
    }
    return randomAccessBufferDatum;
  }

  void writeOutputDatum(String minValueData, String maxValueData) {
    try {
      FileWriter writeToParentNode = new FileWriter("InputToParentNode.txt");
      writeToParentNode.write(minValueData);
      writeToParentNode.write(System.getProperty("line.separator"));
      writeToParentNode.write(maxValueData);
      writeToParentNode.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error in writing file");
    } catch (UnsupportedEncodingException e) {
      System.out.println("Error in writing file");
    } catch (IOException e) {
      System.out.println("Error in writing file");
    }
  }

  float getTemperatureValue(String allValue) {
    int count = 0;
    float temperatureValue = 0;
    StringTokenizer token = new StringTokenizer(allValue, ",");
    while (token.hasMoreTokens()) {
      count++;
      if (count == 4) {
        temperatureValue = Integer.parseInt(token.nextToken());
      } else {
        token.nextToken();
      }
    }
    return temperatureValue;
  }

  char getUnitOfMeasure(String allValue) {
    int count = 0;
    char unitOfMeasure = 0;
    StringTokenizer token = new StringTokenizer(allValue, ",");
    while (token.hasMoreTokens()) {
      count++;
      if (count == 5) {
        unitOfMeasure = token.nextToken().charAt(0);
      } else {
        token.nextToken();
      }
    }
    return unitOfMeasure;

  }

  // Everything will be converted to Celcius
  float tempConverter(float temperatureValue, char unitOfMeasure) {
    if (unitOfMeasure == 'f' || unitOfMeasure == 'F') {
      temperatureValue = 5 / 9 * (temperatureValue - 32);
    }
    if (unitOfMeasure == 'k' || unitOfMeasure == 'K') {
      temperatureValue = (float) (temperatureValue - 273.15);
    }
    // Should write conversion code
    return temperatureValue;
  }

}
