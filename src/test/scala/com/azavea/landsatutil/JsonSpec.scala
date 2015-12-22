package com.azavea.landsatutil

import org.scalatest._
import com.github.nscala_time.time.Imports._
import spray.json._

import Json._

class JsonSpec extends FunSpec with Matchers {
  def getJson(resource: String): String = {
    val stream = getClass.getResourceAsStream(resource)
    val lines = scala.io.Source.fromInputStream(stream).getLines
    val json = lines.mkString(" ")
    stream.close()
    json
  }

  describe("JsonReaders") {
    it("should deserialize an image json") {
      val json = getJson("/test-image.json")
      val image = json.parseJson.convertTo[LandsatImage]
      image.thumbnailUrl should be ("http://earthexplorer.usgs.gov/browse/landsat_8/2015/015/032/LC80150322015213LGN00.jpg")
    }

    it("should deserialize metadata json") {
      val json = getJson("/test-meta.json")
      val meta = json.parseJson.convertTo[QueryMetadata]
      meta.total should be (173)
    }

    it("should deserialize example json response from api") {
      val json = getJson("/test-response.json")
      val result = json.parseJson.convertTo[QueryResult]

      result.images.size should be (173)
    }
  }
}