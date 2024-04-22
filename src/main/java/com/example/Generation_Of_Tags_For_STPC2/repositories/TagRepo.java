package com.example.Generation_Of_Tags_For_STPC2.repositories;

import com.example.Generation_Of_Tags_For_STPC2.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepo extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT a.id_log, a.creat_log, a.zona, a.n_stan, a.n_brig, a.n_smen,\n" +
            "       a.kod, a.wes, a.plavka, a.length_b, a.change_die, a.h_id_log,\n" +
            "       a.h_plavka, a.k_id_log, a.k_plavka, a.tip, a.rez_in_out,\n" +
            "       a.change_out, a.wire_drawer_in, a.wire_drawer_out, a.print_birka, a.job_die\n" +
            "  FROM mk_plus.gsv_birka_prn a\n" +
            "where a.id_log in (SELECT max(id_log) FROM mk_plus.gsv_birka_prn where\n" +
            "-- zona = Переменная\n" +
            "zona =:area  and\n" +
            "--n_stan = Переменная\n" +
            "n_stan =:millNumber and \n" +
            "kod =:materialCode and\n" +
            "print_birka = 0 and \n" +
            "change_out is not null)\n", nativeQuery = true)
    List<Tag> findByAreaAndMillNumberAndMaterialCode(String area, String millNumber, String materialCode);

    @Query(value = "UPDATE mk_plus.GSV_BIRKA_PRN a SET print_birka = 1 WHERE a.id_log in (SELECT max(id_log)" +
            "FROM mk_plus.gsv_birka_prn where print_birka = 0);", nativeQuery = true)
    List<Tag> printAndUpdateTheLastRow();
}
