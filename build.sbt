name := "ThePhoneCompanyChallenge"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.phone.company")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.2.9",
  "co.fs2" %% "fs2-core" % "3.1.1",
  "co.fs2" %% "fs2-io" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.2.10" % "test",
  "org.typelevel" %% "cats-effect-testing-scalatest" % "1.3.0" % Test,
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % "test"
)