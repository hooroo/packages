(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.10.3" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "5.1.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom {:project     'cljsjs/vega
      :version     +version+
      :description "Vega is a declarative format for creating, saving, and sharing visualization designs"
      :url         "https://vega.github.io/"
      :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
    (download
     :url (format "https://unpkg.com/vega@%s/build/vega.js" +lib-version+)
     :checksum "6b66e0de1af2eecb190586ff9f0cc8db")
    (download
     :url (format "https://unpkg.com/vega@%s/build/vega.min.js" +lib-version+)
     :checksum "d87efcf12e7d192aaf24aed7ca1e2dae")
   (sift :move {(re-pattern "^vega.js$") "cljsjs/development/vega.inc.js"
                (re-pattern "^vega.min.js$") "cljsjs/production/vega.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.vega")
   (pom)
   (jar)
   (validate)))
