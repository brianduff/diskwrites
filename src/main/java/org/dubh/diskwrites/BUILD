load("@engage//generator:engage.bzl", "engage_java_library")

engage_java_library(
    name = "Flags",
    java_package = "org.dubh.diskwrites",
    properties = ":flags.hjson"
)

java_library(
    name = "diskwrites",
    srcs = glob(["*.java"]),
    deps = [ ":Flags", "@engage//runtime/src/main/java/org/dubh/engage", "@maven//:com_google_guava_guava" ]
)

java_binary(
    name = "DiskWrites",
    main_class = "org.dubh.diskwrites.DiskWrites",
    runtime_deps = [ ":diskwrites" ]
)
