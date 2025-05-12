% Vegetales
vegetal(brocoli_hervido, 55).
vegetal(zanahoria, 41).
vegetal(pepino, 12).
vegetal(tomate, 18).

% Legumbres
legumbre(lentejas, 353).
legumbre(soja, 446).
legumbre(guisante, 81).

% Frutas
fruta(papaya, 45).
fruta(melon, 31).
fruta(platano, 89).
fruta(fresa, 32).

% Cereales
cereal(pan_centeno, 241).
cereal(arroz_blanco, 129).
cereal(avena, 389).
cereal(quinoa, 374).

% Bebidas
bebida(zumo_naranja, 42).
bebida(agua, 0).
bebida(jugo_uva, 64).
bebida(cafe, 1).

% Huevo
huevo(huevo_estrellado, 147).

% Cárnicos
carnico(chicharron, 601).
carnico(pollo, 237).
carnico(cordero, 292).

% Pastas
pasta(pasta_huevo, 368).
pasta(pasta_semola, 361).
pasta(integral, 338).
pasta(cocida, 131).

% Postres
postre(flan_vainilla, 102).
postre(gelatina, 76).
postre(pastel, 265).
postre(brownie, 379).

% Lácteos
lacteo(leche_semidescremada, 49).
lacteo(queso, 145).
lacteo(yogur, 63).

% Colaciones
colacion(barrita_cereal, 95).
colacion(galleta_integral, 50).
colacion(fruta_seca_30g, 160).
colacion(galleta_agua, 35).

% Regla para armar el desayuno
desayuno(D1, D2, D3, D4, KCal) :-
    fruta(D1, K1), cereal(D2, K2), bebida(D3, K3), huevo(D4, K4),
    KCal is K1 + K2 + K3 + K4.

% Regla para armar el almuerzo
almuerzo(A1, A2, A3, A4, KCal) :-
    (carnico(A1, K1); legumbre(A1, K1)),
    pasta(A2, K2),
    vegetal(A3, K3),
    bebida(A4, K4),
    KCal is K1 + K2 + K3 + K4.

% Regla para armar la comida
comida(C1, C2, C3, KCal) :-
    carnico(C1, K1), pasta(C2, K2), postre(C3, K3),
    KCal is K1 + K2 + K3.

% Regla para armar la merienda
merienda(M1, M2, KCal) :-
    lacteo(M1, K1), colacion(M2, K2),
    KCal is K1 + K2.

% Regla para armar la cena
cena(L1, L2, L3, KCal) :-
    (huevo(L1, K1); carnico(L1, K1)),
    vegetal(L2, K2),
    bebida(L3, K3),
    KCal is K1 + K2 + K3.



% Regla que genera lista de menus
dietas(Gasto, ListaMenus) :-
    findall(menu([D1,D2,D3,D4],[A1, A2, A3, A4],[C1,C2,C3],[M1,M2],[L1, L2, L3],TotalKCal), (
        desayuno(D1,D2,D3,D4,K1),
        K1 >= Gasto*0.2, K1 =< Gasto*0.3,
        almuerzo(A1,A2,A3,A4,K2),
        K2 >= Gasto*0.1, K2 =< Gasto*0.15,
        comida(C1,C2,C3,K3),
        K3 >= Gasto*0.3, K3 =< Gasto*0.4,
        merienda(M1,M2,K4),
        K4 >= Gasto*0.1, K4 =< Gasto*0.15,
        cena(L1,L2,L3,K5),
        K5 >= Gasto*0.2, K5 =< Gasto*0.25,

        %Total dentro de +-10%
        TotalKCal is K1 + K2 + K3 + K4 + K5,
        Inferior is Gasto * 0.9,
        Superior is Gasto * 1.1,
        TotalKCal >= Inferior,
        TotalKCal =< Superior
    ), ListaMenus).
