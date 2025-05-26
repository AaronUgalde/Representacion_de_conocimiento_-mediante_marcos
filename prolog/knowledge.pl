% BASE DE CONOCIMIENTOS: HORMIGAS
% Taxonomía enfocada en hormigas marabunta y ganaderas

frame(reino_animalia,
      subclase_de(top),
      propiedades([tiene(necesita(respirar))]),
      descripcion('Los animales son organismos multicelulares')).

frame(filo_arthropoda,
      subclase_de(reino_animalia),
      propiedades([tiene(exoesqueleto), tiene(segmentado)]),
      descripcion('Los artrópodos tienen exoesqueleto quitinoso y cuerpo segmentado')).

frame(clase_insecta,
      subclase_de(filo_arthropoda),
      propiedades([tiene(tres_partes_cuerpo), tiene(seis_patas)]),
      descripcion('Los insectos tienen cabeza, tórax y abdomen, y seis patas')).

frame(orden_hymenoptera,
      subclase_de(clase_insecta),
      propiedades([tiene(alas_membranosas)]),
      descripcion('Himenópteros con dos pares de alas membranosas')).

frame(familia_formicidae,
      subclase_de(orden_hymenoptera),
      propiedades([tiene(eusocialidad), tiene(antenas_codo)]),
      descripcion('Hormigas: insectos eusociales con antenas en ángulo')).

% Subfamilias clave
frame(subfamilia_myrmicinae,
      subclase_de(familia_formicidae),
      propiedades([tiene(pedicelo_discoidal)]),
      descripcion('Myrmicinae: subfamilia con segundo segmento pedicelar en forma de disco')).

frame(subfamilia_formicinae,
      subclase_de(familia_formicidae),
      propiedades([tiene(ácido_fomico)]),
      descripcion('Formicinae: poseen glándula productora de ácido fórmico; incluye hormigas ganaderas')).

frame(subfamilia_dorylinae,
      subclase_de(familia_formicidae),
      propiedades([tiene(caza_colectiva)]),
      descripcion('Dorylinae: hormigas marabunta, aventureras de caza agresiva')).

% Géneros representantes
frame(genero_atta,
      subclase_de(subfamilia_myrmicinae),
      propiedades([tiene(cultiva_hongos)]),
      descripcion('Atta: hormigas cortadoras de hojas que cultivan hongos')).

frame(genero_eciton,
      subclase_de(subfamilia_dorylinae),
      propiedades([tiene(migratorias), tiene(colonias_timeless)]),
      descripcion('Eciton: hormigas marabunta neotropicales, migratorias y depredadoras')).

frame(genero_formica,
      subclase_de(subfamilia_formicinae),
      propiedades([tiene(cría_pulgones)]),
      descripcion('Formica: hormigas ganaderas que crían pulgones para extraerles melaza')).

frame(genero_solenopsis,
      subclase_de(subfamilia_myrmicinae),
      propiedades([tiene(cola_larga)]),
      descripcion('Solenopsis: incluye hormigas de fuego invasoras')).

frame(genero_camponotus,
      subclase_de(subfamilia_formicinae),
      propiedades([tiene(grandes_tamaño)]),
      descripcion('Camponotus: llamadas hormigas carpinteras')).

% Especies destacadas
frame(att_cephalotes,
      subclase_de(genero_atta),
      propiedades([especie('Atta cephalotes')]),
      descripcion('Hormiga cortadora de hojas que cultiva hongos')).

frame(sol_invicta,
      subclase_de(genero_solenopsis),
      propiedades([especie('Solenopsis invicta')]),
      descripcion('Hormiga de fuego, invasora y con picadura dolorosa')).

frame(ecit_burchellii,
      subclase_de(genero_eciton),
      propiedades([especie('Eciton burchellii')]),
      descripcion('Hormiga marabunta centroamericana, famosa por sus columnas depredadoras')).

frame(for_rufa,
      subclase_de(genero_formica),
      propiedades([especie('Formica rufa')]),
      descripcion('Hormiga ganadera europea, cría pulgones y protege pastos')).

frame(cam_pennsylvanicus,
      subclase_de(genero_camponotus),
      propiedades([especie('Camponotus pennsylvanicus')]),
      descripcion('Hormiga carpintera negra grande')).

% Especies adicionales
frame(att_texana,
      subclase_de(genero_atta),
      propiedades([especie('Atta texana')]),
      descripcion('Hormiga cortadora de hojas norteamericana que cultiva hongos')).

frame(att_sexdens,
      subclase_de(genero_atta),
      propiedades([especie('Atta sexdens')]),
      descripcion('Hormiga cortadora de hojas sudamericana, una de las mayores')).

frame(ecit_hamatum,
      subclase_de(genero_eciton),
      propiedades([especie('Eciton hamatum')]),
      descripcion('Hormiga marabunta de caza arborícola con gran agresividad')).

frame(ecit_lucanoides,
      subclase_de(genero_eciton),
      propiedades([especie('Eciton lucanoides')]),
      descripcion('Marabunta amazónica conocida por forrajes de gran tamaño')).

frame(for_polyctena,
      subclase_de(genero_formica),
      propiedades([especie('Formica polyctena')]),
      descripcion('Hormiga ganadera europea, forma grandes montículos en bosques')).

frame(for_fusca,
      subclase_de(genero_formica),
      propiedades([especie('Formica fusca')]),
      descripcion('Hormiga ganadera oscura, frecuente en suelos y madera muerta')).

frame(sol_geminata,
      subclase_de(genero_solenopsis),
      propiedades([especie('Solenopsis geminata')]),
      descripcion('Hormiga de fuego tropical, invasora y de picadura intensa')).

frame(cam_rufipes,
      subclase_de(genero_camponotus),
      propiedades([especie('Camponotus rufipes')]),
      descripcion('Hormiga carpintera rojiza que anida en madera seca')).

frame(cam_castaneus,
      subclase_de(genero_camponotus),
      propiedades([especie('Camponotus castaneus')]),
      descripcion('Hormiga carpintera de tono castaño que excava en madera')).

