package com.softensity.service

object PrimeNumberService {


  def findPrimeNumberFunctionally(number: Int): Seq[Int] = {
    (1 to number).filter(isPrime).toList
  }

  def findPrimeNumberStream(number: Int): List[Int] = {
    (2 #:: LazyList.from(3,2).filter(isPrime).takeWhile(_ <= number)).toList
  }

  private def isPrime(number: Int): Boolean = {
    (number > 1) && !(2 to Math.sqrt(number).toInt).exists(number % _ == 0)
  }
}
