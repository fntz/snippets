
package hot {
  object MyObject {

    def method(x: BigInt, y: BigInt) = {
      if (x == y) {
        -1
      } else {
        x * y
      }
    }

    def method1(x: Long, y: Long) = {
      if (x == y) {
        -1L
      } else {
        x * y
      }
    }

    def method3: BigInt = {
      def factorial(number: BigInt): BigInt = {
        if (number == BigInt(1))
          return BigInt(1)
        number * factorial(number - 1)
      }

      factorial(BigInt(3000))
    }

  }


}


