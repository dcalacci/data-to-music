(ns data-music.core)

(use '[overtone.core]
     '[overtone.inst.sampled-piano])
(boot-server)

(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io]
         '[overtone.core]
         '[overtone.inst synth]
         '[data-music.instruments])

;; don't require using the namespace before functions!
(refer 'data-music.instruments)


(defn -main
  [& args]
  (sin-wave)
  (noisey))


(defn saw-beat [start] (for [i (range  start (+ start 20))] (at (+ (now) (* i 20)) (saw-wave i))))
(saw-beat)

;; play a chord progression on our piano
(let [time (now)]
  (at time (saw-beat 100))
  (at (+ 1000000 time) (saw-beat 120))
  (at (+ 20000000 time) (saw-beat 140)))

(let [time (now)]
  (at time (play-chord (chord :D3 :major7)))
  (at (+ 2000 time) (play-chord (chord :A3 :major)))
  (at (+ 3000 time) (play-chord (chord :A3 :major7)))
  (at (+ 4300 time) (play-chord (chord :F3 :major7))))
