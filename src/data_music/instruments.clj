(ns data-music.instruments)

(use '[overtone.core]
     '[overtone.inst.drum])


(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(definst square-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-pulse:ar freq)
     vol))

(definst noisey [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (pink-noise) ; also have (white-noise) and others...
     vol))

(definst triangle-wave [freq 440 attack 0.01 sustain 0.1 release 0.4 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-tri freq)
     vol))

(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
    (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
       (sin-osc freq)
       vol))

(saw-wave)

(def sample-data [125 193 182 62])

;; plays given frequencies with the given data, separator, and function
(defn play-notes [data & {:keys [sep fn] :or {sep 500 fn saw-wave}}]
  (for [i data]
    (do (fn i)
        (Thread/sleep sep))))


;; this is good
(defn play-notes [freqs & {:keys [time sep fn] :or {time (now) sep 500 fn saw-wave}}]
  (let [freq (first freqs)]
    (when freq
      (at time (fn freq)))
    (let [next-time (+ time sep)]
      (apply-by next-time play-notes [(rest freqs) :sep sep :fn fn]))))

;; works okay
(do (play-notes sample-data :sep 500 :fn triangle-wave)
    (play-notes sample-data :sep 500 :fn saw-wave))

(kick)


(def one-twenty-bpm (metronome 120))
; this function will play our sound at whatever tempo we've set our metronome to 
(defn looper [nome sound]
    (let [beat (nome)]
        (at (nome beat) (sound))
        (apply-by (nome (inc beat)) looper nome sound [])))

(do
  (looper (metronome 70) saw-wave)
  (looper (metronome 60) saw-wave)
  (looper (metronome 120) hat3))

(stop)

(type kick)

(def sw (saw-wave 80))
