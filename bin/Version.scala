val version = scala.util.Properties.scalaPropOrElse("version.number", "unknown").toString match {
  case "2.10.0" => "2.10"
  case "2.10.2" => "2.10"
  case "2.10.3" => "2.10"
  case "2.10.4" => "2.10"
  case "2.11.0" => "2.11"
  case "2.11.1" => "2.11"
  case e: String => e
}
println(version)
