
import java.lang.{Math => m}

package threads {

  class MyRunnable1 extends Runnable {
    override def run(): Unit = {
      while(true) {
        m.tan(m.atan(m.tan(m.atan(m.tan(m.atan(System.nanoTime()))))))
      }
    }
  }

  class MyRunnable2 extends Runnable {
    override def run(): Unit = {
      while (true) {
        1 + 1
      }
    }
  }


}
