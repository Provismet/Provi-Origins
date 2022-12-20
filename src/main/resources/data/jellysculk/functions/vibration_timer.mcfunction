power grant @e[scores={jellysculk_counter=0}] jellysculk:is_detected
scoreboard players add @e[scores={jellysculk_counter=..100}] jellysculk_counter 1
power revoke @e[scores={jellysculk_counter=101..}] jellysculk:is_detected
scoreboard players reset @e[scores={jellysculk_counter=101..}] jellysculk_counter