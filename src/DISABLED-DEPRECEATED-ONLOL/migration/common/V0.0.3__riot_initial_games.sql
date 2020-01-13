/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

SET search_path TO common,logging,onriot,onlol,ontft,onlor,onlwr;
INSERT INTO riotgame (id, gamename, description)
VALUES (1, 'LoL', 'League of Legends'),
       (2, 'TFT', 'TeamFight Tactics'),
       (3, 'LoR', 'Legends of Runaterra'),
       (4, 'LWR', 'Legends of Legends: WildRift');