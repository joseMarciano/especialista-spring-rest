package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    @Modifying
    @Query("UPDATE Usuario c SET c.senha = :novaSenha WHERE c.id = :id")
    void alterarSenha(@Param("id") Long id, @Param("novaSenha") String novaSenha);

    @Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM USUARIOS WHERE EMAIL = :email AND ID <> :id", nativeQuery = true)
    int existsByEmail(@Param("email") String email, @Param("id") Long id);

    @Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM USUARIOS WHERE EMAIL = :email", nativeQuery = true)
    int existsByEmail(@Param("email") String email);
}
