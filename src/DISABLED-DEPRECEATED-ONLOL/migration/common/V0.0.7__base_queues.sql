/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

SET search_path TO common,logging,onriot,onlol,ontft,onlor,onlwr;
INSERT INTO queue (keyname)
VALUES ('RANKED_SOLO_5x5'),
       ('RANKED_FLEX_SR'),
       ('RANKED_FLEX_TT');