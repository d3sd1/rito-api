SET search_path TO common,logging,onriot,onlol,ontft,onlor,onlwr;
INSERT INTO common.apikey (apikey, banned, valid, lasttimestampused, retryafter, invalidcalls)
VALUES ('RGAPI-1fee5177-2856-46d9-9519-7716861487fe', false, true, 0, 0, 0),
       ('RGAPI-d65da2c7-5339-421e-822c-5b4e36b1dc7f', false, true, 0, 0, 0),
       ('RGAPI-bf12658b-d9c8-45d1-8016-caca0b5d0d62', false, true, 0, 0, 0);