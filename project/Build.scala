import sbt._
import Keys._

object B extends Build {
  lazy val root =
    Project("root", file("."))
      .configs(FunTest)
      .settings(inConfig(FunTest)(Defaults.testTasks) : _*)
      .settings(
        libraryDependencies += specs,
        testOptions in Test := Seq(Tests.Filter(unitFilter)),
        testOptions in FunTest := Seq(Tests.Filter(itFilter))
      )

  def itFilter(name: String): Boolean = (name endsWith "IntegrationSpec")
  def unitFilter(name: String): Boolean = (name endsWith "Spec") && !itFilter(name)

  lazy val FunTest = config("fun") extend(Test)
  lazy val specs = "org.specs2" %% "specs2" % "2.0" % "test"
}
