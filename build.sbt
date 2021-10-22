name := "ThePhoneCompanyChallenge"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.phone.company")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.2.9",
  "co.fs2" %% "fs2-core" % "3.1.1",
  "co.fs2" %% "fs2-io" % "3.1.1"
)