package diginamic;

import static org.junit.jupiter.api.Assertions.*;

import diginamic.entites.Compte;
import diginamic.exception.BanqueException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BanqueServiceTest {

  @Test
  void creerCompteThrowsBanqueExceptionWhenCompteExists() throws BanqueException {
    CompteDao mockCompteDao = Mockito.mock(CompteDao.class);

    BanqueService banqueService = new BanqueService();
    banqueService.setCompteDao(mockCompteDao);

    //    String numCompte = "FR123";
    //    Mockito.when(mockCompteDao.findByNumero(numCompte))
    //        .thenReturn(new Compte("FR123", "test@mail.com", 100.00));

    Mockito.when(mockCompteDao.findByNumero(Mockito.anyString())).thenReturn(new Compte());

    assertThrows(
        BanqueException.class, () -> banqueService.creerCompte("FR123", 100.00, "test@mail.com"));
  }

  @Test
  void creerCompteReturnsCompteWhenCompteDoesntExist() throws BanqueException {
    CompteDao mockCompteDao = Mockito.mock(CompteDao.class);
    BanqueService banqueService = new BanqueService();
    banqueService.setCompteDao(mockCompteDao);

    Mockito.when(mockCompteDao.findByNumero(Mockito.anyString())).thenReturn(null);

    String numCompte = "FR123";
    double soldeCompte = 100.00;
    String emailCompte = "test@mail.com";

    Compte compte = banqueService.creerCompte(numCompte, soldeCompte, emailCompte);
    assertNotNull(compte);
    assertEquals(numCompte, compte.getNumero());
    assertEquals(emailCompte, compte.getEmail());
    assertEquals(soldeCompte, compte.getSolde(), 0.001);
  }
}
