/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

SET search_path TO common,logging,onriot,onlol,ontft,onlor,onlwr;
INSERT INTO leaguetier (id, keyname)
VALUES (1, 'CHALLENGER'),
       (2, 'GRANDMASTER'),
       (3, 'MASTER'),
       (4, 'DIAMOND'),
       (5, 'PLATINUM'),
       (6, 'GOLD'),
       (7, 'SILVER'),
       (8, 'BRONZE'),
       (9, 'IRON');