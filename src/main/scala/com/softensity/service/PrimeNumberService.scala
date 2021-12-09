package com.softensity.service

import org.apache.log4j.Logger

class PrimeNumberService {
  val LOGGER = Logger.getLogger(this.getClass.getName)

  def findPrimeNumberFunctionally(numberTo: Int): Seq[Int] = {
    LOGGER.debug(s"Looking for all primary numbers to $numberTo functionally")
    (1 to numberTo).filter(isPrime).toList
  }

  def findPrimeNumberStream(numberTo: Int): List[Int] = {
    LOGGER.debug(s"Looking for all primary numbers to $numberTo streaming")
    (2 #:: LazyList.from(3, 2).filter(isPrime).takeWhile(_ < numberTo)).toList
  }

  private def isPrime(numberTo: Int): Boolean = {
    LOGGER.debug(s"Checking number $numberTo for primary")
    if (numberTo <= 1) false
    else if (numberTo == 2) true
    else !(2 to Math.sqrt(numberTo).toInt).exists(numberTo % _ == 0)
   }
}
