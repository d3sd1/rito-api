/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

SET search_path TO common,logging,onriot,onlol,ontft,onlor,onlwr;
DELETE
FROM onlol.leagueentry
WHERE summoner_id IS null;