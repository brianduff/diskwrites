workspace(name="diskwrites")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.0"
RULES_JVM_EXTERNAL_SHA = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "junit:junit:4.13",
        "com.google.guava:guava:19.0",
        "org.json:json:20180813",
        "com.github.spullara.mustache.java:compiler:0.9.6",
        "org.hjson:hjson:3.0.0",
    ],
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

ENGAGE_TAG = "0.3"
ENGAGE_SHA = "aaaecccae46f32e56b420fcc1bbf7ba2d978da7e25be5a340cf123605e21c98c"

http_archive(
    name = "engage",
    urls = ["https://github.com/brianduff/engage/archive/%s.zip" % ENGAGE_TAG],
    sha256 = ENGAGE_SHA,
    strip_prefix = "engage-%s" % ENGAGE_TAG
)