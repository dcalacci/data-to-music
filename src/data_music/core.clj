(ns data-music.core)

(use 'overtone.core)
(boot-server)

(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io]
         '[overtone.core]
         '[overtone.inst synth])


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
    (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
       (sin-osc freq)
       vol))

(defn -main
  [& args]
  (sin-wave))
