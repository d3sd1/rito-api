package com.onlol.fetcher.api.filler;

import com.onlol.fetcher.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerFiller {
    @Autowired
    private SummonerRepository summonerRepository;

    /*
    TODO: pensar algo... ya que la actualizacion completa tiene que ser con el mismo api key o dar un api key a cada invocador para que les
    actualice individualmente... o algo que se me ocurra :)

    public void fillSummoner(ApiSummonerDTO apiSummonerDTO) {
        Summoner summoner = this.summonerRepository.findByAccountId()

        if (retrievedSummoner != null) {
            Optional<Summoner> opSummoner = this.summonerRepository.findById(retrievedSummoner.getId());
            if (opSummoner.isPresent()) {
                summoner = opSummoner.get();
                summoner.setLastTimeUpdated(LocalDateTime.now());
                summoner = this.summonerRepository.save(summoner);
                // Update historical name if needed
                if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                    SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                    summonerNameHistorical.setName(summoner.getName());
                    summonerNameHistorical.setSummoner(summoner);
                    this.summonerNameHistoricalRepository.save(summonerNameHistorical);
                }
            }
        }
    }   */
}
