package StudentGradeCalc;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;


//Refactoring project for SENG 350 Asignment 2 
//All commented out code is from the orginal project
//Other comments are to explain what refactoring methods where used to change the code in the given section

public class StudentGradeCalculator {
    final private Map<Integer, List<Pair<String, Boolean>>> allYearsTeachers = Map.ofEntries(
        new AbstractMap.SimpleImmutableEntry<>(
            2020,
            List.of(
                new Pair<>("Josefina", true),
                new Pair<>("Edonisio", true),
                new Pair<>("Edufasio", false)
            )
        ),
        new AbstractMap.SimpleImmutableEntry<>(
            2019,
            List.of(
                new Pair<>("Eduarda", false),
                new Pair<>("Abelardo", false),
                new Pair<>("Francisca", false)
            )
        )
    );
    private final int                                       yearToCalculate;

    public StudentGradeCalculator(final int yearToCalculate) {
        this.yearToCalculate = yearToCalculate;
    }

    public float calculateGrades(final List<Pair<Integer, Float>> examsGrades, final boolean hasReachedMinimumClasses) {
     
    	/*Orginal Method Beofore Refactoring
    	if (!examsGrades.isEmpty()) {
            float gradesSum       = gradesSum(examsGrades);
            int   gradesWeightSum = gradesWeightSum(examsGrades);

            if (gradesWeightSum == 100) {
                if (hasReachedMinimumClasses) {
                    if (hasToIncreaseOneExtraPoint()) {
                        return Float.min(10f, gradesSum + 1);
                    } else {
                        return gradesSum;
                    }
                } else {
                    return 0f;
                }
            } else if (gradesWeightSum > 100) {
                return -1f;
            } else {
                return -2f;
            }
        } else {
            return 0f;
        }
    }
	*/
    	
    	//Refactored Method uses less variables and code to complete the same (extract and shorten methods)
    	//tasks after extracting some functions into separte method and exceptions (guard clause)
    	 
       
    	//if (examsGrades.isEmpty()) {
    	//Replace Control Flag With Break Refactoring 
    	//boolean hasNotDoneAnyExam = examsGrades.isEmpty();
        //if (hasNotDoneAnyExam) {
    	
    	//Decompose & Consolidate Conditional Statements
    	 boolean hasDoneAnyExam = !examsGrades.isEmpty();
         if (!hasDoneAnyExam) {
              return 0f;
          }

          if (!hasReachedMinimumClasses) {
              return 0f;
          }

          //ensureGradesWeightSumIs100percent(examsGrades);
         //float gradesSum = gradesSum(examsGrades);
          
        //Replace Control Flag With Break Refactoring 
          ensureGradesWeightsIsValid(examsGrades);
          

          if (hasToIncreaseOneExtraPoint()) {
             // return Float.min(10f, gradesSum + 1);
        	  
        	  //Replace Control Flag With Break Refactoring 
        	  float increasedGrade = gradesSum(examsGrades) + 1;

              return Float.min(10f, increasedGrade);
          }

          //return gradesSum;
          return gradesSum(examsGrades);
      }
    

    //New Extracted Method Orginally done by the calculateGrades() method and Uses the new exceptions (guard clause)
      private void ensureGradesWeightSumIs100percent(List<Pair<Integer, Float>> examsGrades) {
          int gradesWeightSum = gradesWeightSum(examsGrades);

          if (gradesWeightSum > 100) {
        	  //Removed from if statement in calc grades method and now uses an exception (guard clause)
              throw new GradesWeightOverMaxException();
          } else if (gradesWeightSum < 100) {
        	//Removed from if statement in calc grades method and now uses an exception (guard clause)
              throw new GradesWeightBelowMinException();
          }
      }
      
   //Extracted Method Orginally done by the calculateGrades() method (extract and shorten methods)
    private float gradesSum(List<Pair<Integer, Float>> examsGrades) {
        float gradesSum = 0f;
        for (Pair<Integer, Float> examGrade : examsGrades) {
            gradesSum += (examGrade.first() * examGrade.second() / 100);
        }
        return gradesSum;
    }
    
   //Extracted Method Orginally done by the calculateGrades() method (extract and shorten methods)
    private int gradesWeightSum(List<Pair<Integer, Float>> examsGrades) {
    	
        //return examsGrades.stream().map(Pair::first).reduce(Integer::sum).get();
    	
    	//Decompose & Consolidate Conditional Statements
    	 return examsGrades.stream().map(Pair::first).reduce(Integer::sum).orElse(0);
    }
    
    //Extracted Method Orginally done by the calculateGrades() method (extract and shorten methods)
    private boolean hasToIncreaseOneExtraPoint() {
        boolean hasToIncreaseOneExtraPoint = false;

        for (Map.Entry<Integer, List<Pair<String, Boolean>>> yearlyTeachers : allYearsTeachers.entrySet()) {
            
        	/*
        	if (!(yearToCalculate != yearlyTeachers.getKey())) {
                List<Pair<String, Boolean>> teachers = yearlyTeachers.getValue();

                for (Pair<String, Boolean> teacher : teachers) {
                    if (teacher.second() != true) {
                        continue;
                    }
                    hasToIncreaseOneExtraPoint = true;
                }
            } else {
            */
        	 //Replace Control Flag With Break Refactoring 
        	   if (yearToCalculate != yearlyTeachers.getKey()) {
                continue;
            }
        	   List<Pair<String, Boolean>> teachers = yearlyTeachers.getValue();

               for (Pair<String, Boolean> teacher : teachers) {
                   Boolean isBenevolent = teacher.second();
                   
                   
                  // if (isBenevolent) {
                   
                 //Decompose & Consolidate Conditional Statements
                   boolean isEvenYear   = yearToCalculate % 2 == 0;

                   if ((isBenevolent && isEvenYear) || teacher.first().equals("Núria")) {
                       return true;  
                   
                   }
               }
           }
        //return hasToIncreaseOneExtraPoint;
        return false;
    }
}