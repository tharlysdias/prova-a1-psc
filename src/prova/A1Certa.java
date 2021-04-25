package prova;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 
 * @author Tharlys de Souza Dias
 *
 */
public class A1Certa {
	
	// Contadores
	static int quantidadeBeneficiario = 0;
	
	static double valorTotalBeneficio;
	
	static int quantidadeMesesBeneficio = 0;
	
	static double RankingBeneficioUm;
	static double RankingBeneficioDois;
	static String RankingBeneficioNomeUm;
	static String RankingBeneficioNomeDois;
	
	static int RankingTempoUm;
	static int RankingTempoDois;
	static String RankingTempoNomeUm;
	static String RankingTempoNomeDois;

	public static void main(String[] args) throws ParseException {
		
		/*
		 * Requisitos da prova A1
		 * A - O benefício só será concedido para maiores de 18 anos;						OK
		 * H - Se estiver desempregado há menos de 6 meses terá uma redução de 10%;			OK
		 * L - O benefício será de 6 meses para desempregados;								OK
		 * R - O Benefício será de 10 meses para empregadores com até 10 funcionários;		OK
		 * S - O benefício de pessoas que moram em Pernambuco terá acréscimo de 14%;		OK
		 * T - O benefício de pessoas que moram no Pará terá acréscimo de 9%;				OK
		 * Y - O Benefício será de 5 meses para empregados com mais de 50 anos;				OK
		 */

		String continuar;

		do {
			System.out.println("Bem-vindo ao sistema de auxílio\n");

			metodoGeral();
			
			System.out.print("\n\nVocê deseja informar um novo beneficiário (Sim ou Não): ");
			continuar = entradaTexto();
			
			quantidadeBeneficiario++;

		} while (continuar.equalsIgnoreCase("Sim") || continuar.equalsIgnoreCase("S"));
		
		
		System.out.println("\nTotal de beneficiários: " + quantidadeBeneficiario);
        System.out.println("Total de valor que será concedido: " + valorTotalBeneficio);
        System.out.println("Primeiro maior valor do benefício: " + RankingBeneficioNomeUm);
        System.out.println("Segundo maior valor do benefício: " + RankingBeneficioNomeDois);
        System.out.println("Primeiro maior tempo que irá receber os benefício: " + RankingTempoNomeUm);
        System.out.println("Segundo maior tempo que irá receber os benefício: " + RankingTempoNomeDois);
		System.out.println("\nVocê saiu. Obrigado por usar nosso sistema!\n");

	}
	
	public static void metodoGeral() throws ParseException {
		
		String nomeCompleto, dataNascimento = null, categoria = null, uf, opcao = null;
		boolean empregado = false, empregador = false, desempregado = false, aposentado = false;
		int idade, numeroCategoria, quantidadeFuncionarios = 0, mesesBeneficioMaximoEMinimo;
		double valorAuxilio = 0.00D;
		
		// Entrada de dados
		System.out.print("Informe o nome completo: ");
		nomeCompleto = entradaTexto();

		System.out.print("Informe a data de nascimento do beneficiário (Ex.: dd/mm/aaaa): ");
		dataNascimento = entradaTexto();
		
		// Chama o método para calcular a data
		idade = calculaIdade(dataNascimento);
		
		while (idade < 18) {
			System.out.println("O beneficiário deve ter mais de 18 anos");
			System.out.print("Informe a data de nascimento do beneficiário (Ex.: dd/mm/aaaa): ");
			dataNascimento = entradaTexto();
			idade = calculaIdade(dataNascimento);
		}
		
		
		System.out.println("Informe a categoria do beneficiário: ");
		System.out.println("1. Empregado ");
		System.out.println("2. Empregador ");
		System.out.println("3. Desempregado ");
		numeroCategoria = entradaNumeroInteiro();
		
		while (numeroCategoria != 1 && numeroCategoria != 2 && numeroCategoria != 3) {
			System.out.println("Informe uma categoria correta, conforme abaixo: ");
			System.out.println("1. Empregado ");
			System.out.println("2. Empregador ");
			System.out.println("3. Desempregado ");
			numeroCategoria = entradaNumeroInteiro();
		}
		
		switch (numeroCategoria) {
		case 1:
			categoria = "Empregado";
			System.out.print("É aposentado? (Sim/Não): ");
			opcao = entradaTexto();
			if (opcao.equalsIgnoreCase("Sim") || opcao.equalsIgnoreCase("S")) {
				aposentado = true;
			}
			break;
			
		case 2:
			categoria = "Empregador";
			System.out.print("Informe a quantidade de funcionários da sua empresa: ");
			quantidadeFuncionarios = entradaNumeroInteiro();
			break;
			
		case 3:
			categoria = "Desempregado";
			desempregado = true;
			break;
		}
		
		
		do {
			System.out.print("Informe o UF do benefíciario (Ex.: SC): ");
			uf = entradaTexto();
        } while(!validaUf(uf));
		
		
		boolean continuaLoop = false;
		do {
			System.out.print("Quantidade de meses do beneficío, no mínimo 2 meses e no máximo 12 meses: ");
			mesesBeneficioMaximoEMinimo = entradaNumeroInteiro();
			
			if (mesesBeneficioMaximoEMinimo >= 2) {
				if (mesesBeneficioMaximoEMinimo <= 12) {
					continuaLoop = true;
				}
			}
		} while (!continuaLoop);
		
		
		valorAuxilio = calculaAuxilioBase(categoria, quantidadeFuncionarios, desempregado);
		
		valorAuxilio = beneficioPorUfPe(valorAuxilio, uf);
		valorAuxilio = beneficioPorUfPa(valorAuxilio, uf);
		
		System.out.println("\nO benefíciario: " + nomeCompleto);
		System.out.println("Data de nascimento: " + dataNascimento);
		System.out.println("Residente no Estado de: " + uf);
		System.out.println("A categoria do benefíciario é: " + categoria);
		System.out.println("O valor do auxílio mensal é R$ " + valorAuxilio);
		
		if (uf.equalsIgnoreCase("PE")) {
			System.out.println("O benefício para quem mora em Pernambuco tem acréscimo de 14%");
		}
		if (uf.equalsIgnoreCase("PA")) {
			System.out.println("O benefício para quem mora no Pará tem acréscimo de 9%");
		}
		
		quantidadeMesesBeneficioDesempregado(categoria, valorAuxilio);
		empregador(categoria, quantidadeFuncionarios, valorAuxilio);
		empregadosComMaisDeCinquentaAnos(categoria, valorAuxilio);
		
		if (!uf.equalsIgnoreCase("PE") && !uf.equalsIgnoreCase("PA")) {
			if (quantidadeMesesBeneficio != 5) {
				if (quantidadeMesesBeneficio != 6) {
					if (quantidadeMesesBeneficio != 10) {
						valorTotalBeneficio += valorAuxilio * mesesBeneficioMaximoEMinimo;
						System.out.println("O seu benefício será de " + mesesBeneficioMaximoEMinimo + " meses.");						
					}
				}
			}
		}
		
		
		// valorTotalBeneficio += valorAuxilio;
		
		if (RankingBeneficioUm < valorAuxilio || RankingBeneficioUm == 0.00D) {
            
			RankingBeneficioUm = valorAuxilio;
            RankingBeneficioNomeUm = nomeCompleto;
        }

        if (RankingBeneficioDois < valorAuxilio && RankingBeneficioUm > valorAuxilio || RankingBeneficioDois == 0.00 || RankingBeneficioDois == RankingBeneficioUm) {
            
        	RankingBeneficioDois = valorAuxilio;
            RankingBeneficioNomeDois = nomeCompleto;
        }

        if (RankingTempoUm < quantidadeMesesBeneficio || RankingTempoUm == 0) {
            RankingTempoUm = quantidadeMesesBeneficio;
            RankingTempoNomeUm = nomeCompleto;
        }

        if (RankingTempoDois < quantidadeMesesBeneficio && RankingTempoUm > quantidadeMesesBeneficio || RankingTempoDois == 0 || RankingTempoDois == RankingTempoUm) {

            RankingTempoDois = quantidadeMesesBeneficio;
            RankingTempoNomeDois = nomeCompleto;
        }
	}
	
	/**
	 * Regra de negócio Específica: A
	 * 
	 * O benefício só será concedido para maiores de 18 anos
	 * 
	 * Método que faz o calculo da data de nascimento
	 * 
	 * @param dataNasc		Pacote java.util.Date
	 * @return idadeAno		Retorna um inteiro
	 * @throws ParseException 
	 */ 
	public static int calculaIdade(String dataNascimento) throws ParseException {
		
		// Setando formato necessário para data
		SimpleDateFormat formatoDataNascimento = new SimpleDateFormat("dd/MM/yyyy");
		// Faz um parse para transformar a data de nascimento informada pelo usuario
		Date dataNasc = formatoDataNascimento.parse(dataNascimento);
		
		Calendar nascimento = new GregorianCalendar();

		nascimento.setTime(dataNasc);

		// Cria um objeto calendar com a data atual
		Calendar dataHoje = Calendar.getInstance();

		// Obtém a idade baseado no ano
		int idadeAno = dataHoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);

		nascimento.add(Calendar.YEAR, idadeAno);

		// Se a data de hoje é antes da data de Nascimento, então diminui 1
		if (dataHoje.get(Calendar.MONTH) < nascimento.get(Calendar.MONTH)) {
			idadeAno--;
		} else {
			if (dataHoje.get(Calendar.MONTH) == nascimento.get(Calendar.MONTH)
					&& dataHoje.get(Calendar.DAY_OF_MONTH) < nascimento.get(Calendar.DAY_OF_MONTH)) {
				idadeAno--;
			}
		}
		return idadeAno;
	}
	
	/**
	 * Regra de negócio Específica: L
	 * 
	 * O benefício será de 6 meses para desempregados
	 * 
	 * @param categoria
	 * @param valorAuxilio
	 */
	public static void quantidadeMesesBeneficioDesempregado(String categoria, double valorAuxilio) {
		if (categoria.equalsIgnoreCase("Desempregado")) {
			System.out.println("O seu benefício será de 6 meses no valor total de R$ " + (valorAuxilio * 6));
			quantidadeMesesBeneficio = 6;
			valorTotalBeneficio += valorAuxilio * quantidadeMesesBeneficio;
		}
	}
	
	/**
	 * Regra de negócio Específica: H
	 * 
	 * Se estiver desempregado há menos de 6 meses terá uma redução de 10%
	 * 
	 * @return valorBeneficio		Tipo double
	 */
	public static double desempregadoDesconto() {
		double valorDesconto;
		double valorBeneficio = 0.00D;
		double porcentagemDesconto = 0.10D;
		double valorUsuario;
		int quantidadeMesesDesempregado;
		
		System.out.print("Informe o valor do benefício entre R$ 1.500 e R$ 2.300: ");
		valorUsuario = entradaNumeroDouble();
		
		boolean sair = true;
		while (sair == true) {
			
			if (valorUsuario >= 1500.00D && valorUsuario <= 2300.00D) {
				valorBeneficio = valorUsuario;
				sair = false;
			} else {
				System.out.print("Informe o valor entre R$ 1.500 e R$ 2.300: ");
				valorUsuario = entradaNumeroDouble();
				sair = true;
			}
		}
		
		System.out.print("Há quantos meses está desempregado: ");
		quantidadeMesesDesempregado = entradaNumeroInteiro();
		
		if (quantidadeMesesDesempregado < 6) {
			valorDesconto = valorBeneficio * porcentagemDesconto;
			valorBeneficio -= valorDesconto;
		}
		
		return valorBeneficio;
	}
	
	/**
	 * Regra de negócio Específica: R
	 * 
	 * O Benefício será de 10 meses para empregadores com até 10 funcionários
	 * 
	 * Método que varifica por quantos meses o empregador
	 * irá receber o auxilio com base na quantidade de funcionarios
	 * 
	 * @param categoria
	 * @param quantidadeFuncionarios
	 * @param valorAuxilio
	 */
	public static void empregador(String categoria, int quantidadeFuncionarios, double valorAuxilio) {
		if (categoria.equalsIgnoreCase("Empregador") && quantidadeFuncionarios <= 10) {
			System.out.println("O seu benefício será de 10 meses no valor total de R$ " + (valorAuxilio*10));
			quantidadeMesesBeneficio = 10;
			valorTotalBeneficio += valorAuxilio * quantidadeMesesBeneficio;
		}
	}
	
	/**
	 * Regra de negócio Específica: S
	 * 
	 * Recalcula o valor do benefício com base na UF
	 * 
	 * O benefício de pessoas que moram em Pernambuco terá acréscimo de 14%
	 * 
	 * @param valorAuxilio
	 * @param uf
	 * @return valorAuxilio
	 */
	public static double beneficioPorUfPe(double valorAuxilio, String uf) {
		double valorPe = 0.14D;
		if (uf.equalsIgnoreCase("PE")) {
			return valorAuxilio + (valorAuxilio * valorPe);
		} else {
			return valorAuxilio;			
		}
	}
	
	/**
	 * Regra de negócio Específica: T
	 * 
	 * Recalcula o valor do benefício com base na UF
	 * 
	 * O benefício de pessoas que moram no Pará terá acréscimo de 9%
	 * 
	 * @param valorAuxilio
	 * @param uf
	 * @return valorAuxilio
	 */
	public static double beneficioPorUfPa(double valorAuxilio, String uf) {
		double valorPa = 0.09D;
		if (uf.equalsIgnoreCase("PA")) {
			return valorAuxilio + (valorAuxilio * valorPa);
		} else {
			return valorAuxilio;			
		}
	}
	
	
	/**
	 * Regra de negócio Específica: Y
	 * 
	 * O Benefício será de 5 meses para empregados com mais de 50 anos
	 * 
	 * @param categoria
	 * @param valorAuxilio
	 */
	public static void empregadosComMaisDeCinquentaAnos(String categoria, double valorAuxilio) {
		if (categoria.equalsIgnoreCase("Empregado")) {
			System.out.println("O seu benefício será de 5 meses no valor total de R$ " + (valorAuxilio * 5));
			quantidadeMesesBeneficio = 5;
			valorTotalBeneficio += valorAuxilio * quantidadeMesesBeneficio;
		}
	}

	/**
	 * Método que calcula o auxilio com base na categoria, quantidade de funcionario
	 * ou desempregado
	 * 
	 * @param categoria
	 * @param quantidadeFuncionario
	 * @param desempregado
	 * @return valorBeneficio		Retorna um double
	 */
	public static double calculaAuxilioBase(String categoria, int quantidadeFuncionario, boolean desempregado) {

		double valorBeneficio = 0.00D;
		double valorUsuario;
		double valor = 200.00D;

		if (categoria.equalsIgnoreCase("Desempregado") && desempregado) {
			valorBeneficio = desempregadoDesconto();

		} else if (categoria.equalsIgnoreCase("Empregador")) {
			valorBeneficio = valor * quantidadeFuncionario;

		} else if (categoria.equalsIgnoreCase("Empregado")) {
			System.out.print("Para empregados, informe o valor do benefício entre R$ 1.000 e R$ 1.800: ");
			valorUsuario = entradaNumeroDouble();
			
			boolean sair = true;
			while (sair == true) {
				
				if (valorUsuario >= 1000 && valorUsuario <= 1800) {
					valorBeneficio = valorUsuario;
					sair = false;
				} else {
					System.out.print("Informe um valor entre R$ 1.000 e R$ 1.800: ");
					valorUsuario = entradaNumeroDouble();
					sair = true;
				}
			}

		} else {
			valorBeneficio = 0.00D;
		}
		return valorBeneficio;

	}

	/**
	 * Método que recebe os dados do usuário
	 * 
	 * Foi implementado este método pois o nextLine (Scanner) estava apresentado
	 * alguns erros não esperados, nesta solução conseguimos consertar
	 * 
	 * @return inputText		Retorna uma String
	 */
	public static String entradaTexto() {
		Scanner teclado = new Scanner(System.in);
		String entradaTexto = teclado.nextLine();

		return entradaTexto;
	}
	
	public static int entradaNumeroInteiro() {
		Scanner teclado = new Scanner(System.in);
		int numeroInteiro = teclado.nextInt();
		
		return numeroInteiro;
	}
	
	public static double entradaNumeroDouble() {
		Scanner teclado = new Scanner(System.in);
		double numeroDouble = teclado.nextDouble();
		
		return numeroDouble;
	}
	
	 /**
     * Valida a UF informada
     * 
     * @param uf
     * @return Arrays.asList(arrayDeUfs).contains(uf)
     */
    public static boolean validaUf (String uf) {

        String[] arrayDeUfs = {
            "AC",
            "AL",
            "AP",
            "AM",
            "BA",
            "CE",
            "DF",
            "ES",
            "GO",
            "MA",
            "MS",
            "MT",
            "MG",
            "PA",
            "PB",
            "PR",
            "PE",
            "PI",
            "RJ",
            "RN",
            "RS",
            "RO",
            "RR",
            "SC",
            "SP",
            "SE",
            "TO"
        };

        return Arrays.asList(arrayDeUfs).contains(uf);

    }

}