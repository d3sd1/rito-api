SET search_path TO global,logging,onriot,onlol,ontft,onlor,onlwr;
INSERT INTO global.platform (serviceregion, serviceplatform, hostname)
VALUES ('br', 'br1', 'br1.api.riotgames.com'),
       ('eune', 'eun1', 'eun1.api.riotgames.com'),
       ('euw', 'euw1', 'euw1.api.riotgames.com'),
       ('jp', 'jp1', 'jp1.api.riotgames.com'),
       ('kr', 'kr1', 'kr.api.riotgames.com'),
       ('lan', 'la1', 'la1.api.riotgames.com'),
       ('las', 'la2', 'la2.api.riotgames.com'),
       ('na', 'na', 'na1.api.riotgames.com'),
       ('na', 'na1', 'na1.api.riotgames.com'),
       ('oce', 'oce1', 'oc1.api.riotgames.com'),
       ('tr', 'tr1', 'tr1.api.riotgames.com'),
       ('ru', 'ru1', 'ru.api.riotgames.com'),
       ('pbe', 'pbe1', 'pbe1.api.riotgames.com');