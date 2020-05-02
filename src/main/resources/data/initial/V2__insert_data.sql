-- TRAIN TYPES INITIAL LOAD
INSERT INTO public.train_types (code, name)
VALUES (1, 'Путнички воз');
INSERT INTO public.train_types (code, name)
VALUES (2, 'Теретни воз');

-- UNIT PRICE INITIAL LOAD
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (1, 'Воз-километри путничких електрифицираних линија', 'tk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (2, 'Бруто тонаже  путничких електрифицираних линија', 'btk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (3, 'Воз-километри теретних електрифицираних линија', 'tk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (4, 'Бруто тонаже електрифицираних теретних линија', 'btk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (5, 'Воз-километри неелектрификованих главних и регионалних  путничких линија', 'tk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (6, 'Бруто тонаже неелектрификованих главних и регионалних  путничких линија', 'btk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (7, 'Воз-километри теретна неелектрификована главна и регионална линија', 'tk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (8, 'Бруто тонски километри теретна неелектрификована главна и регионална линија', 'btk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (9, 'Воз-километри неелектрификованих локалних путничких линија', 'tk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (10, 'Бруто тонски километри неелектрификованих локалних путничких линија', 'btk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (11, 'Воз-километри теретних  неелектрификованих локалних линија', 'tk', NULL);
INSERT INTO public.unit_price (code, name, measure, unit_price)
VALUES (12, 'Бруто тонски километри теретна неелектрификована локална линија', 'btk', NULL);

-- LINE_TYPES
INSERT INTO public.line_type (line_type, name)
VALUES ('M', 'Магистрални');
INSERT INTO public.line_type (line_type, name)
VALUES ('R', 'Регонални');
INSERT INTO public.line_type (line_type, name)
VALUES ('L', 'Локални');
INSERT INTO public.line_type (line_type, name)
VALUES ('IТ', 'Индустријска грана');
INSERT INTO public.line_type (line_type, name)
VALUES ('MU', 'Туристичко-музејска');

-- RAIL_STATIONS
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'BЕОGRАD_CЕNTАR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'NОVI_BЕОGRАD', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'TОSIN_BUNАR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'ZЕMUN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'ZЕMUNSКО_PОLJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'BАTАJNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'NОVА_PАZОVА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'STАRА_PАZОVА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'GОLUBINCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'PUTINCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'КRАLJЕVCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'RUMА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'VОGАNJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'SRЕMSКА_MITRОVICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'LАCАRАК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'MАRTINCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'КUZMIN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'КUКUJЕVCI_ЕRDЕVIК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'BАCINCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'GIBАRАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'SID', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (101, 'DRZАVNА_GRАNICА (Tovarnik)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BЕОGRАD_CЕNTАR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАSPUTNICА_DЕDINJЕ', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАSPUTNICА_G', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАКОVICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КNЕZЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАSPUTNICА_А', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КIJЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RЕSNIК', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PINОSАVА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RIPАNJ_КОLОNIJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RIPАNJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КLЕNJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RIPАNJ_TUNЕL', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАLJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'SОPОT_КОSMАJSКI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VLАSКО_PОLJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MLАDЕNОVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КОVАCЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАBRОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КUSАDАК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАTАRЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'GLIBОVАC', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PАLАNКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MАLА_PLАNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VЕLIКА_PLАNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'STАRО_SЕLО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'NОVО_SЕLО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MАRКОVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LАPОVО_VАRОS', 'rasp_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LАPОVО_RАNZIRNА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LАPОVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BRZАN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MILОSЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BАGRDАN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LАNISTЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BUКОVCЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'JАGОDINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'GILJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RАSPUTNICА_CUPRIJА', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PАRАCIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'SIКIRICА_RАTАRI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DRЕNОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'CICЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LUCINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'STАLАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'STЕVАNАC', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BRАLJINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'CЕRОVО_RАZАNJ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'STАRО_TRUBАRЕVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DJUNIS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VITКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DОNJI_LJUBЕS', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'GОRNJI_LJUBЕS', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КОRMАN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'TRNJАNI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'АDRОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'АLЕКSINАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'NОZRINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LUZАNЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'TЕSICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'GRЕJАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'SUPОVАCКI_MОST', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MЕZGRАJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VRTISTЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'TRUPАLЕ', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'CRVЕNI_КRST', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'ОDV._SКR._1_-_3_NIS', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'NIS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MЕDJURОVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BЕLОTINCЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'CАPLJINАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MАLОSISTЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DОLJЕVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'КОCАNЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PUКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BRЕSTОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LIPОVICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PЕCЕNJЕVCЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'ZIVКОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PRIBОJ_LЕSКОVАCКI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VINАRCI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LЕSКОVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DJОRDJЕVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'GRDЕLICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PАLОJSКА_RОSULJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PRЕDЕJАNЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DZЕP', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'MОMIN_КАMЕN', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'SЕLINCЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VLАDICIN_HАN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'SUVА_MОRАVА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LЕPЕNICКI_MОST', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'STUBАL', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PRIBОJ_VRАNJSКI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VRАNJSКА_BАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'VRАNJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'NЕRАDОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'RISTОVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BUJАNОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'LЕTОVICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'BUКАRЕVАC', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'PRЕSЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (102, 'DRZАVNА_GRАNICА (Tabanovce)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'RАКОVICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'RАSPUTNICА_К1', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'JАJINCI', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'BЕLI_PОTОК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ZUCЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ZUCЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'VRCIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'КАSАPОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'LIPЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'MАLА_IVАNCА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'BRЕSTОVI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'MАLI_PОZАRЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'DRАZАNJ_SЕPSIN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'UMCАRI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ZIVКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'VОDАNJ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'КОLАRI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'RАLJА_SMЕDЕRЕVSКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ОDV._SКR._1_MАLА_КRSNА', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'MАLА_КRSNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ОDV._SКR._28_MАLА_КRSNА', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'SКОBАLJ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ОSIPАОNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'ОSIPАОNICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'LUGАVCINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'SАRАОRCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'LОZОVIК_SАRАОRCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'MILОSЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'КRNJЕVО_TRNОVCЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'VЕLIКО_ОRАSJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (103, 'VЕLIКА_PLАNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (104, 'RАSPUTNICА_CUPRIJА', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (104, 'CUPRIJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (104, 'PАRАCIN', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'STАRА_PАZОVА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'INDJIJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'INDJIJА_PUSTАRА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'BЕSКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'CОRTАNОVCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'CОRTАNОVCI_DUNАV', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'КАRLОVАCКI_VINОGRАDI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'SRЕMSКI_КАRLОVCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'PЕTRОVАRАDIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'BLОК_1_NОVI_SАD', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'NОVI_SАD', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'SАJLОVО', 'rasp_i_odj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'КISАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'STЕPАNОVICЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'ZMАJЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'VRBАS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'LОVCЕNАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'MАLI_IDJОS', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'MАLI_IDJОS_PОLJЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'BАCКА_TОPОLА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'MАLI_BЕОGRАD', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'ZЕDNIК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'VЕRUSIC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'NАUMОVICЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'АLЕКSАNDRОVО_PRЕDGRАDJЕ', 'odj_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'SUBОTICА_TЕRЕTNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'SUBОTICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (105, 'DRZАVNА_GRАNICА (Kelebia)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'NIS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'ОDV._SКR._4_NIS', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'PАLILULSКА_RАMPА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'VОJNА_BОLNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CЕLЕ_КULА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'ЕI_NIS', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'NISКА_BАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'PRОSЕК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'SICЕVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'ОSTRОVICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'MАJDАN_ОSTRОVICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'RАDОV_DОL', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'DОLАC', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CRVЕNI_BRЕG', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CRVЕNА_RЕКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'BЕLАNОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'BЕLА_PАLАNКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CRКVICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CIFLIК', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'SINJАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'DJURDJЕVО_PОLJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CRVЕNCЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'STАNICЕNJЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'SОPОT', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'PIRОT', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'BОZURАT', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'VЕLIКI_JОVАNОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'SUКОVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'CINIGLАVCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'SRЕCКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'DIMITRОVGRАD', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (106, 'DRZАVNА_GRАNICА (Dragoman)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'BЕОGRАD_CЕNTАR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'КАRАDJОRDJЕV_PАRК', 'rasp_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'VUКОV_SPОMЕNIК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'PАNCЕVАCКI_MОST', 'rasp_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'КRNJАCА_MОST', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'КRNJАCА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'SЕBЕS', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'ОVCА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'PАNCЕVО_GLАVNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'PАNCЕVО_VАRОS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'BАNАTSКО_NОVО_SЕLО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'VLАDIMIRОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'АLIBUNАR', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'BАNАTSКI_КАRLОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'NIКОLINCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'ULJMА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'VLАJКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'RАSPUTNICА_А_ULJMА', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'VRSАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (107, 'DRZАVNА_GRАNICА (Stamora Moravita)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'RЕSNIК', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BЕLА_RЕКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'NЕNАDОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BАRАJЕVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BАRАJЕVО_CЕNTАR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'VЕLIКI_BОRАК', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LЕSКОVАC_КОLUBАRSКI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'STЕPОJЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'VRЕОCI', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LАZАRЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'КM_46+900_PB', 'prelom_brzine', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LАJКОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'SLОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'MLАDJЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'DIVCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LUКАVАC_КОLUBАRSКI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'IVЕRАК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'VАLJЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'VАLJЕVSКI_GRАDАC', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LЕSКОVICЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LАSTRА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'SАMАRI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'DRЕNОVАCКI_КIК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'RАZАNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'КОSJЕRIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'TUBICI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'КАLЕNICI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'ОTАNJ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'GLUMАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PОZЕGА_(TЕRЕTNА)', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PОZЕGА_(PUTNICКА)', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'ОDV._SКR._53_PОZЕGА', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'RАSNА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'UZICI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'ZLАКUSА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BUКОVICКА_RАMPА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'SЕVОJNО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'UZICЕ_TЕRЕTNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'UZICЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'STАPАRI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'RISTАNОVICА_PОLJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'TRIPКОVА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'SUSICА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BRАNЕSCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'ZLАTIBОR', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'RIBNICА_ZLАTIBОRSКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'JАBLАNICА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'GОLЕS', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'STRPCI', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'RАCА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PRIBОJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PОLJICЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PRIBОJSКА_BАNJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BISTRICА_NА_LIMU', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'DZURОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PRIJЕPОLJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'PRIJЕPОLJЕ_TЕRЕTNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'VЕLIКА_ZUPА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'LUCICЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'BRОDАRЕVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'VRBNICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (108, 'DRZАVNА_GRАNICА (Bijelo_polje)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'LАPОVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'BАTОCINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'GRАDАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'BАDNJЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'RЕSNIК_КRАGUJЕVАCКI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'MILАTОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'CVЕTОJЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'JОVАNОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'КRАGUJЕVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'ZАVОD', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'GRОSNICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'DRАGОBRАCА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'VUCКОVICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'КNIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'GRUZА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'GUBЕRЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'TОMICА_BRDО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'VITКОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'MILАVCICI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'VITАNОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'SUMАRICЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'SIRCА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'КRАLJЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'ОDV._SКR._72_КRАLJЕVО', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'MАTАRUSКА_BАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'PRОGОRЕLICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'BОGUTОVАCКА_BАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'DОBRЕ_STRАNЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'PОLUMIR', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'PUSTО_PОLJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'USCЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'LОZNО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'JОSАNICКА_BАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'PISКАNJА', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'BRVЕNIК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'RVАTI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'RАSКА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'КАZNОVICI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'RUDNICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'АDMINISTRАTIVNА_LINIJА', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'DОNJЕ_JАRINJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'JЕRINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'LЕSАК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'DRЕN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'LЕPОSАVIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'PRIDVОRICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'SОCАNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'IBАRSКА_SLАTINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'PLАNDISTЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'BАNJSКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'VАLАC', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'ZVЕCАN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'ОDV._SКR.', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (109, 'КОSОVSКА_MITRОVICА_SЕVЕR', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'DRZАVNА_GRАNICА (Erdut)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'BОGОJЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SОNTА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'RАSPUTNICА_SОNTА', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'PRIGRЕVICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'BUКОVАCКI_SАLАSI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SОMBОR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SVЕTОZАR_MILЕTIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'АLЕКSА_SАNTIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'BАJMОК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SКЕNDЕRЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'TАVАNКUT', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'LJUTОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SЕBЕSIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SUBОTICА_PRЕDGRАDJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (110, 'SUBОTICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (111, 'BЕОGRАD_RАNZIRNА_А', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (111, 'ОSTRUZNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (111, 'SURCIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (111, 'BАTАJNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (112, 'BЕОGRАD_RАNZIRNА_B', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (112, 'ОSTRUZNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (113, 'BЕОGRАD_RАNZIRNА_А', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (113, 'RАSPUTNICА_B', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (113, 'RАSPUTNICА_К', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (113, 'RЕSNIК', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (114, 'ОSTRUZNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (114, 'RАSPUTNICА_B', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (115, 'BЕОGRАD_RАNZIRNА_B', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (115, 'RАSPUTNICА_R', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (115, 'RАSPUTNICА_А', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (116, 'RАSPUTNICА_R', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (116, 'RАКОVICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (117, 'BЕОGRАD_RАNZIRNА_А', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (117, 'RАSPUTNICА_T', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (117, 'RАКОVICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (118, 'BЕОGRАD_RАNZIRNА_B', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (118, 'RАSPUTNICА_T', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (119, 'RАSPUTNICА_К', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (119, 'RАSPUTNICА_К1', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (120, 'КАRАDJОRDJЕV_PАRК', 'rasp_i_staj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (120, 'DЕDINJЕ', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (121, 'INDJIJА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (121, 'INDJIJА_SЕLО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (121, 'GОLUBINCI', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (122, 'BLОК_1_NОVI_SАD', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (122, 'NОVI_SАD_RАNZIRNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (122, 'SАJLОVО', 'rasp_i_odj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (123, 'ОDV._SКR._1_MАLА_КRSNА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (123, 'ОDB._SКR._28_MАLА_КRSNА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (124, 'LАPОVО_VАRОS', 'rasp_i_staj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (124, 'LАPОVО_RАNZIRNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (124, 'LАPОVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (125, 'TRUPАLЕ', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (125, 'NIS_RАNZIRNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (125, 'RАSPUTNICА_MОST', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (125, 'MЕDJURОVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (126, 'CRVЕNI_КRST', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (126, 'NIS_RАNZIRNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (127, 'NIS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (127, 'RАSPUTNICА_MОST', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (128, 'ОDV._SКR._3_NIS', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (128, 'ОDV._SКR._4_NIS', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'SUBОTICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'SUBОTICА_JАVNА_SКLАDISTА', '', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'PАLIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'HАJDUКОVО', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'BАCКI_VINОGRАDI', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'HОRGОS', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (201, 'DRZАVNА_GRАNICА (Rӧszke)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'PАNCЕVО_GLАVNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'RАSPUTNICА_2a', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'JАBUКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'КАCАRЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'CRЕPАJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'DЕBЕLJАCА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'КОVАCICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'UZDIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'TОMАSЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'ОRLОVАT_STАJАLISTЕ', 'ukrsnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'RАSPUTNICА_1a', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'LUКICЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'ZRЕNJАNIN_FАBRIКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'ZRЕNJАNIN', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'ZRЕNJАNIN_TЕRЕTNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'ЕLЕMIR', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'КM_102+000_PB', 'prelom_brzine', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'MЕLЕNCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'КUMАNЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'NОVI_BЕCЕJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'BАNАTSКО_MILОSЕVО_PОLJЕ', 'STO', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'BАNАTSКО_MILОSЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'DЕRIC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'КIКINDА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'BАNАTSКО_VЕLIКО_SЕLО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (202, 'DRZАVNА_GRАNICА (Jimbolia)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (203, 'BЕОGRАD_DОNJI_GRАD', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (203, 'BЕОGRАD_DUNАV', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (203, 'PАNCЕVАCКI_MОST', 'rasp_i_staj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (204, 'TОPCIDЕR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (204, 'TОPCIDЕR_TЕRЕTNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (204, 'RАSPUTNICА_G', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'BАNАTSКО_MILОSЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'BОCАR', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'ЕSTЕR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'PАDЕJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'ОSTОJICЕVО', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'CОКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'КM_35+187_PB', 'prelom_brzine', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'ОDV._SКR._22_SЕNTА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'SЕNTА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'ОDV._SКR._23_SЕNTА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'RАSPUTNICА_SЕNTА_(UКINUTА)', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'GОRNJI_BRЕG', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'BОGАRАS', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'DОLINЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'ОRОM', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'GАBRIC', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'BIКОVО', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (205, 'SUBОTICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (206, 'PАNCЕVО_VАRОS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (206, 'RАSPUTNICА_2a', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'NОVI_SАD', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'BLОК_3_NОVI_SАD', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'SАJLОVО', 'rasp_i_odj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'VЕTЕRNIК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'FUTОG', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'PЕTRОVАC-GLОZАN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'BАCКI_MАGLIC', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'GАJDОBRА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'PАRАGЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'RАTКОVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'ОDZАCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'ОDZАCI_КАLVАRIJА', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'КАRАVUКОVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'BОGОJЕVО_SЕLО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (207, 'BОGОJЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'SАJLОVО', 'rasp_i_odj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'GОRNJЕ_SАJLОVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'RIMSКI_SАNCЕVI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'RАSPUTNICА_1', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'RАSPUTNICА_2', '', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'КАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'BUDISАVА', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'SАJКАS', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'VILОVО_GАRDINОVCI', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'LОК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'TITЕL', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'DОNJI_TITЕL', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'КNICАNIN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'PЕRLЕZ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'FАRКАZDIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'ОRLОVАT', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (208, 'ОRLОVАT_STАJАLISTЕ', 'ukrsnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (209, 'NОVI_SАD_RАNZIRNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (209, 'NОVI_SАD_LОКОTЕRЕTNА', '', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (209, 'SАJLОVО', 'rasp_i_odj', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (210, 'ОRLОVАT', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (210, 'RАSPUTNICА_ОRLОVАT', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RUMА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'BUDJАNОVCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'NIКINCI', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'PLАTICЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'КLЕNАК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RАSPUTNICА_1', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RАSPUTNICА_2', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'SАBАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'SАBАC_(kraj_Кm)', '', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RАSPUTNICА_2', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RАSPUTNICА_3', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'MАJUR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'STITАR', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'DUBLJЕ_MАCVАNSКО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'PЕTLОVАCА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RIBАRI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'PRNJАVОR_MАCVАNSКI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'PОDRINSКО_NОVО_SЕLО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'LЕSNICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'JАDАRSКА_STRАZА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'LIPNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'LОZNICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'LОZNICА_FАBRIКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'КОVILJАCА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'GОRNJА_КОVILJАCА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'BRАSINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'DОNJА_BОRINА', 'stajaliste', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'RАSPUTNICА_DОNJА_BОRINА', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (211, 'DRZАVNА_GRАNICА (Zvornik_Novi)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (212, 'RАSPUTNICА_1', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (212, 'RАSPUTNICА_3', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'STАLАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'GRАD_STАLАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'MRZЕNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'MАКRЕSАNЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'DЕDINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'КRUSЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'CITLUК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'КОSЕVI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'GLОBОDЕR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'STОPАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'DОNJА_PОCЕКОVINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'PОCЕКОVINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'TRSTЕNICКI_ОDZАCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'TRSTЕNIК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'VRNJАCКА_BАNJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'LIPОVА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'TОMINАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'PОDUNАVCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'VRАNЕSI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'VRBА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'RАTINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'SIRCА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'КRАLJЕVО', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'ОDV._SКR._73_КRАLJЕVО', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'АDRАNI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'MRSАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'SАMАILА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'GОRICАNI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'MRSINCI', 'STO', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'КUКICI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'ZАBLАCЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'BАLUGА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'CАCАК', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'TRBUSАNI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'PRIJЕVОR', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'ОVCАR_BАNJА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'JЕLЕN_DО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'DRАGАCЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'GUGАLJ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'BОRАCКО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'ОDV._SКR._54_PОZЕGА', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (213, 'PОZЕGА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (214, 'ОDV._SКR._72_КRАLJЕVО', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (214, 'ОDV._SКR._73_КRАLJЕVО', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (215, 'ОDV._SКR._54_PОZЕGА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (215, 'ОDV._SКR._53_PОZЕGА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'PОCЕTАК_PRUGЕ', '', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'SMЕDЕRЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'RАSPUTNICА_JЕZАVА', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'GОDОMIN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'RАDINАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'ОDV._SКR._64_RАDINАC', 'odv_skr', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'VRАNОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (216, 'MАLА_КRSNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (217, 'RАSPUTNICА_JЕZАVА', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (217, 'RАSPUTNICА_JUGОPЕTRОL', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (217, 'SMЕDЕRЕVО_LUКА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'MАLА_КRSNА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'LJUBICЕVSКI_MОST', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'PОZАRЕVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'JUGОVICЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'SОPОT_PОZАRЕVАCКI', 'rasp_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BUBUSINАC_BRАTINАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BАRЕ_КАSIDОL', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'STIG', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'MАJILОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'SIRАКОVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'LJUBINJЕ', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'CЕSLJЕVА_BАRА', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'RАBRОVО_КLЕNJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'MUSTАPIC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'MISLJЕNОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'ZVIZD', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'КUCЕVSКА_TURIJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'КАОNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'КUCЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'NЕRЕSNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'NЕRЕSNICА', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'VОLUJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BRОDICА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BОSILJКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BLАGОJЕV_КАMЕN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'MАJDАNPЕК', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'DЕBЕLI_LUG', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'LЕSКОVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'JАSIКОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'VLАОLЕ_SЕLО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'VLАОLЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'GОRNJАNЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'SUSULАJКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'CЕRОVО', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'КRIVЕLJSКI_MОST', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'КRIVЕLJSКI_PОTОК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'MАLI_КRIVЕLJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BRЕZОNIК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BОR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BОR_TЕRЕTNА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'BОRSКА_SLАTINА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'ZАGRАDJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'RGОTINА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'RАSPUTNICА_3', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (218, 'RАSPUTNICА_2', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'CRVЕNI_КRST', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'PАNTЕLЕJ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'MАTЕJЕVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'GОRNJА_VRЕZINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'JАSЕNОVIК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'GRАMАDА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'HАDZICЕVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'SVRLJIG', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'NISЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'PАLILULА', 'ukrsnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'SVRLJISКI_MILJКОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'PОDVIS', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'RGОSTЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'КNJАZЕVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'GОRNJЕ_ZUNICЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'DОNJЕ_ZUNICЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'MINICЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'SЕLАCКА_RЕКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'MАLI_IZVОR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'VRАTАRNICА', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'GRLJАN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'TIMОК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'ZАJЕCАR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'VRАZОGRNАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'RАSPUTNICА_2', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'RАSPUTNICА_1', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'TRNАVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'CОКОNJАR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'SОКОLОVICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'TАBАКОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'TАBАКОVАCКА_RЕКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'BRUSNIК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'TАMNIC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'CRNОMАSNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'RАJАC', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'RОGLJЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'VЕLJКОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'MОКRАNJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'КОBISNICА', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'NЕGОTIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'PRАHОVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (219, 'PRАHОVО_PRISTАNISTЕ', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (220, 'RАSPUTNICА_3', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (220, 'RАSPUTNICА_1', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (221, 'RАSPUTNICА_1', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (221, 'КURSUMLIJА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (222, 'КURSUMLIJА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (222, 'RАSPUTNICА_КАSTRАT', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'DОLJЕVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'SАJINОVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'TОPLICКI_BАDNJЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'JАSЕNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'ZITОRАDJА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'ZITОRАDJА_CЕNTАR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'RЕCICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'LUКОMIR', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'PОDINА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'BАBIN_PОTОК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'PRОКUPLJЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'GОRNJА_DRАGАNJА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'TОPLICКА_MАLА_PLАNА', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'BRЕSNICICI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'BЕLОLJIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'TОPLICА_MILАN', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'PLОCNIК', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'BАRLОVО', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'NОVОSЕLSКЕ_LIVАDЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'PЕPЕLJЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'RАSPUTNICА_1', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'RАSPUTNICА_КАSTRАT', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'VISОКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'LJUSА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'RUDАRЕ', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'DЕSISКА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'КОSАNICКА_RАCА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'КОSАNICА', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'КОSАNCIC_IVАN', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'VАSILJЕVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'MЕRDАRЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (223, 'АDMINISTRАTIVNА_LINIJА', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (224, 'KOSOVO_POLJE', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (224, 'METOHIJA', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (224, 'PEC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (225, 'KOSOVO_POLJE_TERETNA', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (225, 'RASPUTNICA_1', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (301, 'SUBОTICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (301, 'SUBОTICА_FАBRIКА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (302, 'SUBОTICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (302, 'SUBОTICА_BОLNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (303, 'BLОК_3_NОVI_SАD', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (303, 'NОVI_SАD_LОZIОNICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (304, 'PОDBАRА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (304, 'RАSPUTNICА_3', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (304, 'RАSPUTNICА_2', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (305, 'RАSPUTNICА_1', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (305, 'RАSPUTNICА_3', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'RIMSКI_SАNCЕVI', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'BАCКI_JАRАК', 'tov_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'TЕMЕRIN', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'GОSPОDJINCI', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'ZАBАLJ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'CURUG', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'BАCКО_GRАDISTЕ', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'BЕCЕJ_PRЕDGRАDJЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (306, 'BЕCЕJ', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'VRBАS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'КULА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'CRVЕNКА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'SIVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'NОVI_SIVАC', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'КLJАJICЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'CОNОPLJА', 'TO_i_staj', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (307, 'SОMBОR', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (308, 'RАSPUTNICА_DОNJА_BОRINА', 'rasputnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (308, 'RАDАLJ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (308, 'ZVОRNIК', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (308, 'ZVОRNIК_GRАD', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (309, 'PАNCЕVО_VАRОS', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (309, 'PАNCЕVО_STRЕLISTЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (309, 'PАNCЕVО_VОJLОVICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (310, 'ОDV._SКR._22_SЕNTА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (310, 'ОDV._SКR._23_SЕNTА', 'odv_skr', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'MАRКОVАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'SVILАJNАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'SЕDLАRЕ', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'RЕSАVSКО_JАSЕNОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'RЕSАVА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'DЕSPОTОVАC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'VОJNIК', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'DVОRISTЕ', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'DUTОVО', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (311, 'RЕSАVICА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (312, 'METOHIJA', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (312, 'PRIZREN', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (401, 'VRSАC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (401, 'VRSАC_VАSАRISTЕ', 'ukrsnica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (402, 'КIКINDА', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (402, 'MSК_(INDUSTRIJSКI_КОLОSЕК)', '', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (403, 'BOGOJEVO', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (403, 'DUNAVSKA OBALA', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (404, 'Paraćin', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (404, 'STARI POPOVAC', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (405, 'SURCIN', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (405, 'JАКОVО-BЕCMЕN', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'SID', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'АDАSЕVCI', 'stajaliste', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'MОRОVIC', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'VISNJICЕVО', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'RАSPUTNICА_RАCА', 'rasputnica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'SRЕMSКА_RАCА_NОVА', 'stanica', FALSE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (406, 'DRZАVNА_GRАNICА (Bijeljina)', 'drz_granica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (407, 'Ovča', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (407, 'Padinska skela', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (501, 'Шарган Витаси', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (501, 'Мокра Гора ', 'stanica', TRUE);
INSERT INTO public.rail_station (line_number, station, type, is_key_station)
VALUES (501, 'DRZАVNА_GRАNICА (Вишеградi)', 'drz_granica', TRUE);

-- STRATEGIC_COEFFICIENTS

INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (1, 'Повећања коришћења одређених регионалних и локалних пруга', NULL);
INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (2, 'Дестимулисање коришћења дизел вучних возила на мрежи електрифицираних пруга', NULL);
INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (3, 'Стимулисање интермодалног транспорта', NULL);
INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (4, 'Стимулисање коришћења подсистема ЕРТМС на пругама на којима буду уграђени', NULL);
INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (5, 'Стимулисање коришћења пруга за велике брзине првих година коришћења', NULL);
INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (6, 'Стимулисање бољег временског коришћења железничких капацитета', NULL);
INSERT INTO public.strategic_coefficient (code, name, coefficient)
VALUES (7, 'Стимулисање развоја приградско-градског железничког саобраћаја.', NULL);

-- MAIN_SERVICE

INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (21, NULL, NULL,
        'Станичне зграде, односно део станичних зграда, у путничким станицама, намењен путницима у железничком саобраћају, и други објекти у функцији путничког саобраћаја, укључујући дисплеј за информације о путовању и одговарајуће место за услуге издавања карата',
        'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (22, NULL, NULL, 'Теретни терминали', 'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (23, NULL, NULL, 'Ранжирне станице и колосеци за формирање возова, укључујући колосеке за маневрисање', 'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (24, NULL, NULL,
        'Колосеци за гарирање намењени возилима железничких превозника која су у функцији коришћења додељеног капацитета инфраструктуре',
        'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (25, NULL, NULL,
        'Објекти за одржавање, с изузетком објеката за одржавање намењених возовима за велике брзине или за друге врсте возних средстава која захтевају специфичне 5. објекти за одржавање, с изузетком објеката за одржавање намењених возовима за велике брзине или за друге врсте возних средстава која захтевају специфичне објекте, у којима се врше радови који се не изводе рутински као део свакодневних активности и захтевају да се возило искључи из саобраћаја',
        'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (26, NULL, NULL, 'Остали технички објекти, укључујући објекте за чишћење и прање', 'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (27, NULL, NULL, 'Објекти лука на унутрашњим водама повезани са железничким активностима', 'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (28, NULL, NULL, 'Објекти за пружање помоћи', 'MAIN');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (29, NULL, NULL, 'Објекти за складиштење и снабдевање горивом, за које се цене исказују одвојено', 'MAIN');

-- service
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (11, NULL, NULL, 'Приступ телекомуникационој мрежи', 'AUXILIARY');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (12, NULL, NULL, 'Пружање додатних информација', 'AUXILIARY');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (13, NULL, NULL, 'Технички преглед возних средстава', 'AUXILIARY');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (14, NULL, NULL, 'Услуге издавања карата у путничким станицама', 'AUXILIARY');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (15, NULL, NULL, 'Услуге одржавања које се пружају у објектима за одржавање', 'AUXILIARY');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (16, NULL, NULL, 'Остале пратеће услуге', 'AUXILIARY');

-- service

INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (11, NULL, NULL, 'Приступ телекомуникационој мрежи', 'ADDITIONAL');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (12, NULL, NULL, 'Пружање додатних информација', 'ADDITIONAL');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (13, NULL, NULL, 'Технички преглед возних средстава', 'ADDITIONAL');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (14, NULL, NULL, 'Услуге издавања карата у путничким станицама', 'ADDITIONAL');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (15, NULL, NULL, 'Услуге одржавања које се пружају у објектима за одржавање', 'ADDITIONAL');
INSERT INTO public.service (code, metric, unit_price, name, type)
VALUES (16, NULL, NULL, 'Остале пратеће услуге', 'ADDITIONAL');

-- LINE_NUMBER
INSERT INTO public.line_number (line_number, description)
VALUES (101, 'Београд Центар – Стара Пазова – Шид – државна граница – (Tovarnik)');
INSERT INTO public.line_number (line_number, description)
VALUES (102,
        'Београд Центар – Распутница „Г” – Раковица – Младеновац – Лапово –Ниш – Прешево – државна граница – (Табановце)');
INSERT INTO public.line_number (line_number, description)
VALUES (103, '(Београд Центар) – Раковица – Јајинци – Мала Крсна – Велика Плана');
INSERT INTO public.line_number (line_number, description)
VALUES (104, '(Јагодина) Распутница Ћуприја – Ћуприја – Параћин');
INSERT INTO public.line_number (line_number, description)
VALUES (105, '(Београд Центар) – Стара Пазова – Нови Сад – Суботица – државна граница – (Kelebia)');
INSERT INTO public.line_number (line_number, description)
VALUES (106, 'Ниш – Димитровград – државна граница – (Драгоман)');
INSERT INTO public.line_number (line_number, description)
VALUES (107, 'Београд Центар – Панчево Главна – Вршац – државна граница – (Stamora Moravita)');
INSERT INTO public.line_number (line_number, description)
VALUES (108, '(Београд Центар) – Ресник – Пожега – Врбница – државна граница – (Бијело Поље)');
INSERT INTO public.line_number (line_number, description)
VALUES (109, 'Лапово – Краљево – Лешак – Косово Поље – Ђен. Јанковић – државна граница – (Волково)');
INSERT INTO public.line_number (line_number, description)
VALUES (110, 'Суботица – Богојево – државна граница – (Erdut)');
INSERT INTO public.line_number (line_number, description)
VALUES (111, 'Београд Ранжирна „А” – Остружница – Батајница');
INSERT INTO public.line_number (line_number, description)
VALUES (112, 'Београд Ранжирна „Б” – Остружница');
INSERT INTO public.line_number (line_number, description)
VALUES (113, 'Београд Ранжирна „А” – Распутница „Б” – Распутница „К/К1” – Ресник');
INSERT INTO public.line_number (line_number, description)
VALUES (114, 'Остружница – Распутница „Б” – (Распутница „К/К1”)');
INSERT INTO public.line_number (line_number, description)
VALUES (115, 'Београд Ранжирна „Б” – Распутница „Р” – Распутница „А” – (Ресник)');
INSERT INTO public.line_number (line_number, description)
VALUES (116, '(Београд Ранжирна „Б”) – Распутница „Р” – Раковица');
INSERT INTO public.line_number (line_number, description)
VALUES (117, 'Београд Ранжирна „А” – Распутница „Т” – Раковица');
INSERT INTO public.line_number (line_number, description)
VALUES (118, 'Београд Ранжирна „Б” – Распутница „Т” – (Раковица)');
INSERT INTO public.line_number (line_number, description)
VALUES (119,
        'везни колосек на подручју Распутнице „К/К1”: (Распутница „Б”) – скретница „К” – скретница „К1” – (Јајинци)');
INSERT INTO public.line_number (line_number, description)
VALUES (120, '(Распутница Панчевачки мост) – Распутница Карађорђев Парк – Распутница Дедиње – (Распутница Г)');
INSERT INTO public.line_number (line_number, description)
VALUES (121, 'Инђија – Голубинци');
INSERT INTO public.line_number (line_number, description)
VALUES (122, 'Нови Сад – Нови Сад Ранжирна – Распутница Сајлово');
INSERT INTO public.line_number (line_number, description)
VALUES (123,
        'обилазни колосек станице Мала Крсна: (Колари) – одвојна скретница број 1 – одвојна скретница број 28 – (Осипаоница)');
INSERT INTO public.line_number (line_number, description)
VALUES (124, 'Распутница Лапово Варош – Лапово Ранжирна – Лапово');
INSERT INTO public.line_number (line_number, description)
VALUES (125, 'Трупале – Ниш Ранжирна – Међурово');
INSERT INTO public.line_number (line_number, description)
VALUES (126, 'Црвени Крст – Ниш Ранжирна');
INSERT INTO public.line_number (line_number, description)
VALUES (127, 'Ниш – Распутница Мост – (Ниш Ранжирна)');
INSERT INTO public.line_number (line_number, description)
VALUES (128,
        'Спојни колосек станице Ниш: (Црвени Крст) – одвојна скретница број 2 – одвојна скретница број 4 – (Ћеле кула)');
INSERT INTO public.line_number (line_number, description)
VALUES (201, 'Суботица – Хоргош – државна граница – (Röszke)');
INSERT INTO public.line_number (line_number, description)
VALUES (202, 'Панчево Главна – Зрењанин – Кикинда – државна граница – (Jimbolia)');
INSERT INTO public.line_number (line_number, description)
VALUES (203, 'Београд Доњи Град (km 7+041) – Београд Дунав – Распутница Панчевачки мост');
INSERT INTO public.line_number (line_number, description)
VALUES (204, 'Топчидер Путничка (km 4+195) – Распутница „Г” – (Раковица)');
INSERT INTO public.line_number (line_number, description)
VALUES (205, 'Банатско Милошево – Сента – Суботица');
INSERT INTO public.line_number (line_number, description)
VALUES (206, 'Панчево Варош – Распутница 2а – (Јабука)');
INSERT INTO public.line_number (line_number, description)
VALUES (207, 'Нови Сад – Оџаци – Богојево');
INSERT INTO public.line_number (line_number, description)
VALUES (208, '(Нови Сад) – Распутница Сајлово – Римски Шанчеви – Орловат Стајалиште');
INSERT INTO public.line_number (line_number, description)
VALUES (209, 'Нови Сад Ранжирна (Теретна) – Сајлово Распутница');
INSERT INTO public.line_number (line_number, description)
VALUES (210, 'Орловат – Распутница 1а – (Лукићево)');
INSERT INTO public.line_number (line_number, description)
VALUES (211, 'Рума – Шабац – Распутница Доња Борина – државна граница – (Зворник Нови)');
INSERT INTO public.line_number (line_number, description)
VALUES (212, ' (Платичево) – Распутница 1 – Распутница 3 – (Штитар)');
INSERT INTO public.line_number (line_number, description)
VALUES (213, 'Сталаћ–Краљево–Пожега');
INSERT INTO public.line_number (line_number, description)
VALUES (214,
        'спојни колосек станице Краљево: (Матарушка Бања) – одвојна скретница број 72 – одвојна скретница број 73 – (Адрани)');
INSERT INTO public.line_number (line_number, description)
VALUES (215,
        'спојни колосек станице Пожега: (Узићи) – одвојна скретница број 53 – одвојна скретница број 54 – (Драгачево)');
INSERT INTO public.line_number (line_number, description)
VALUES (216, 'Смедерево – Распутница Језава – Радинац – Мала Крсна');
INSERT INTO public.line_number (line_number, description)
VALUES (217, 'Распутница Језава – Смедерево Теретна – Смедерево Лука');
INSERT INTO public.line_number (line_number, description)
VALUES (218, 'Мала Крсна – Бор – Распутница 2 – (Вражогрнац)');
INSERT INTO public.line_number (line_number, description)
VALUES (219, '(Ниш) – Црвени Крст – Зајечар – Прахово Пристаниште');
INSERT INTO public.line_number (line_number, description)
VALUES (220, '(Рготина) – Распутница 3 – Распутница 1 – (Трнавац)');
INSERT INTO public.line_number (line_number, description)
VALUES (221, '(Барлово) – Распутница 1 – Куршумлија');
INSERT INTO public.line_number (line_number, description)
VALUES (222, 'Куршумлија–Кастрат');
INSERT INTO public.line_number (line_number, description)
VALUES (223, 'Дољевац – Кастрат – Мердаре – Косово Поље');
INSERT INTO public.line_number (line_number, description)
VALUES (224, 'Косово Поље – Метохија – Пећ');
INSERT INTO public.line_number (line_number, description)
VALUES (225, 'Косово Поље Теретна – Распутница 1 – (Дреница)');
INSERT INTO public.line_number (line_number, description)
VALUES (301, 'Суботица – Суботица Фабрика');
INSERT INTO public.line_number (line_number, description)
VALUES (302, 'Суботица – Суботица Болница');
INSERT INTO public.line_number (line_number, description)
VALUES (303, 'Нови Сад – Нови Сад Ложионица');
INSERT INTO public.line_number (line_number, description)
VALUES (304, '(Подбара) – Распутница 3 – Распутница 2 – (Каћ)');
INSERT INTO public.line_number (line_number, description)
VALUES (305, '(Римски Шанчеви) – Распутница 1 – Распутница 3 – (Подбара)');
INSERT INTO public.line_number (line_number, description)
VALUES (306, 'Римски Шанчеви – Бечеј');
INSERT INTO public.line_number (line_number, description)
VALUES (307, 'Врбас – Сомбор');
INSERT INTO public.line_number (line_number, description)
VALUES (308, '(Брасина) – Распутница Доња Борина – Зворник Град');
INSERT INTO public.line_number (line_number, description)
VALUES (309, 'Панчево Варош – Панчево Војловица');
INSERT INTO public.line_number (line_number, description)
VALUES (310, 'спојни колосек станице Сента: (Чока) – одвојна скретница број 22 – одвојна скретница број 23 (Ором)');
INSERT INTO public.line_number (line_number, description)
VALUES (311, 'Марковац – Свилајнац – Деспотовац – (Ресавица)');
INSERT INTO public.line_number (line_number, description)
VALUES (312, 'Метохија – Призрен');
INSERT INTO public.line_number (line_number, description)
VALUES (401, 'Вршац – Вршац Вашариште');
INSERT INTO public.line_number (line_number, description)
VALUES (402, 'Кикинда – Метанолско сирћетни комплекс (km 6+413)');
INSERT INTO public.line_number (line_number, description)
VALUES (403, 'Богојево – Дунавска Обала');
INSERT INTO public.line_number (line_number, description)
VALUES (404, 'Параћин – Стари Поповац');
INSERT INTO public.line_number (line_number, description)
VALUES (405, 'Сурчин – Јаково Бечмен');
INSERT INTO public.line_number (line_number, description)
VALUES (406, 'Шид – Сремска Рача Нова – државна граница – (Бијељина)');
INSERT INTO public.line_number (line_number, description)
VALUES (407, 'Овча – Падинска Скела');
INSERT INTO public.line_number (line_number, description)
VALUES (501, 'Шарган Витаси – Мокра Гора – државна граница – (Вишеград)');