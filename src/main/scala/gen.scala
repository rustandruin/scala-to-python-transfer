package net.thousandfold.test
import org.msgpack.annotation.Message
import org.msgpack.ScalaMessagePack
import breeze.linalg._
import breeze.numerics._
import grizzled.slf4j.Logging
import java.io.FileOutputStream
import java.util.zip.GZIPOutputStream

@Message
class EOFinfo {
    var numeofs: Int = 0
    var numobs: Int = 0
    var numgridpts: Int = 0

    var U : Array[Float] = Array.empty[Float]
    var V : Array[Float] = Array.empty[Float]
    var S : Array[Float] = Array.empty[Float]
    var mean : Array[Float] = Array.empty[Float]
}

object testMsgPack extends Logging{ 

    def main(args: Array[String]) {
        val numeofs = 20
        val numobs = 400
        val numgridpts = 1000

        val Ubrz = DenseMatrix.zeros[Float](numgridpts, numeofs)
        for( row <- 0 until numgridpts; col <- 0 until numeofs) {
            Ubrz(row, col) = row + 0.0f
        }
        val U = Ubrz.toDenseVector.toArray

        val Vbrz = DenseMatrix.zeros[Float](numeofs, numobs)
        for (row <- 0 until numeofs; col <- 0 until numobs) {
            Vbrz(row, col) = col + 0.5f
        }
        val V = Vbrz.toDenseVector.toArray

        val S = DenseVector.range(0, numeofs, 1).toArray.map(x => x.toFloat)
        val mean = sum(Ubrz, Axis._1).toArray

        val obj = new EOFinfo()
        obj.numeofs = numeofs
        obj.numobs = numobs
        obj.numgridpts = numgridpts
        obj.U = U
        obj.V = V
        obj.S = S
        obj.mean = mean

        logger.info("Starting serialization")
        val serialized : Array[Byte] = ScalaMessagePack.write(obj)
        logger.info("Done serialization")

        logger.info("Starting write to file")
        var out = None: Option[FileOutputStream]
        try {
            out = Some(new FileOutputStream("testout.msgpack"))
            out.get.write(serialized)
        } finally {
            if (out.isDefined) out.get.close
        }
        logger.info("Wrote to file")

        logger.info("Starting write to compressed file")
        var out_uncompressed = None: Option[FileOutputStream]
        var out_compressed = None: Option[GZIPOutputStream]
        try {
            out_uncompressed = Some(new FileOutputStream("testout.msgpack.gz"))
            out_compressed = Some(new GZIPOutputStream(out_uncompressed.get))
            out_compressed.get.write(serialized)
        } finally {
            if (out_compressed.isDefined) { 
                out_compressed.get.finish
                out_compressed.get.close 
            }
        }
        logger.info("Finished write to compressed file")

    }
}
