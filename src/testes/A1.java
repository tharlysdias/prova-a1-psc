package testes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class A1 {
	
	// Contadores
		static int quantidadeBeneficiario = 0;
		
		static double valorTotalBeneficio;
		
		static int quantidadeMesesBeneficio = 0;
		
		static double RankingBeneficioZero;
		static double RankingBeneficioUm;
		static String RankingBeneficioNomeZero;
		static String RankingBeneficioNomeUm;
		
		static int RankingTempoZero;
		static int RankingTempoUm;
		static String RankingTempoNomeZero;
		static String RankingTempoNomeUm;
	
	public static void main(String[] args) throws ParseException {

		int idade, numeroCategoria, numeroDeFuncionarios = 0;
		String nome_completo, data_nascimento, categoria = null, UF, opcao = null;
		double valorBeneficio = 0.00;
		boolean empregado = false, empregador = false, desempregado = false, aposentado = false;

		Scanner teclado = new Scanner(System.in);

		do {
			System.out.println("Bem-vindo ao sistema de aux�lio de Governo do Estado\n");

			System.out.print("Digite seu nome completo: ");
			nome_completo = recebeTexto();

			System.out.print("Digite sua data de nascimento (Ex.: dd/MM/yyyy): ");
			data_nascimento = recebeTexto();

			// Formato necess�rio para data
			SimpleDateFormat formatoDataNascimento = new SimpleDateFormat("dd/MM/yyyy");
			// Recebe a data e faz um parse para transformar a data de nascimento informada
			// pelo usuario
			Date dateNascimento = formatoDataNascimento.parse(data_nascimento);
			// Chama o m�todo para calcular a data
			idade = calculaIdade(dateNascimento);

			if (verificaMaioridade(nome_completo, idade) == false) {
				System.out.println("Informe a UF do beneficiado: ");
                UF = recebeTexto();

                while (!validaUf(UF)) {

                    System.out.println("Insira uma UF v�lida:");
                    UF = recebeTexto();

                }
				/*
				 * System.out.print("Informe o seu Estado (UF): "); UF = recebeTexto();
				 */

				System.out.println("Selecione uma categoria abaixo: ");
				System.out.println("1. Empregado");
				System.out.println("2. Empregador");
				System.out.println("3. Desempregado");
				numeroCategoria = recebeValorInteiro();

				switch (numeroCategoria) {
				case 1:
					categoria = "Empregado";
					empregado = true;
					System.out.println("� aposentado? (Sim/N�o): ");
					opcao = recebeTexto();
					if (opcao.equalsIgnoreCase("Sim") || opcao.equalsIgnoreCase("S")) {
						aposentado = true;
					}
					break;

				case 2:
					categoria = "Empregador";
					empregador = true;
					System.out.println("Informe a quantidade de funcion�rios da sua empresa: ");
					numeroDeFuncionarios = recebeValorInteiro();
					break;

				case 3:
					categoria = "Desempregado";
					desempregado = true;
					break;

				default:
					System.out.println("Op��o inv�lida... Voc� saiu da execu��o!");
				}
				valorBeneficio = calculoAuxilioBase(categoria, numeroDeFuncionarios, desempregado);

				if (UF.equalsIgnoreCase("SP")) {
					valorBeneficio += beneficioUFSP(valorBeneficio, UF);
				} else if (UF.equalsIgnoreCase("SC")) {
					valorBeneficio += beneficioUFSC(valorBeneficio, UF);
				} else if (UF.equalsIgnoreCase("MG")) {
					valorBeneficio += beneficioUFMG(valorBeneficio, UF);
				}

				System.out.println("O nome do beneficiario �: " + nome_completo);
				System.out.println("A data de nascimento do beneficiario �: " + data_nascimento);
				System.out.println("O beneficiario � residente da UF: " + UF);
				System.out.println("O valor do auxilio mensal � R$: " + valorBeneficio);

				if (categoria.equalsIgnoreCase("Empregador")) {
					System.out.println("O seu beneficio ser� de 7 meses no valor total de R$: " + (valorBeneficio * 7));
				}

				desempregado(categoria, valorBeneficio);
				empregador(categoria, numeroDeFuncionarios, valorBeneficio);
			}

			System.out.println("\n\nVoc� deseja informar um novo benefici�rio (Sim ou N�o): ");
			opcao = recebeTexto();

		} while  (opcao.equalsIgnoreCase("Sim") || opcao.equalsIgnoreCase("S"));
		
			quantidadeMesesBeneficio = mesesBeneficioEmpregadores(categoria);
			ranking( valorBeneficio, nome_completo,  categoria);	
			

	}	

	public static void ranking(double valorBeneficio, String nome_completo, String categoria) {

		int quantidadeBeneficiario = 0; 		
		
		
		
		valorTotalBeneficio += valorBeneficio;
		
		if (RankingBeneficioZero < valorBeneficio || RankingBeneficioZero == 0.00D) {
            
			RankingBeneficioZero = valorBeneficio;
            RankingBeneficioNomeZero = nome_completo;
        }

        if (RankingBeneficioUm < valorBeneficio && RankingBeneficioZero > valorBeneficio || RankingBeneficioUm == 0.00 || RankingBeneficioUm == RankingBeneficioZero) {
            
        	RankingBeneficioUm = valorBeneficio;
            RankingBeneficioNomeUm = nome_completo;
        }

        if (RankingTempoZero < quantidadeMesesBeneficio || RankingTempoZero == 0) {
            RankingTempoZero = quantidadeMesesBeneficio;
            RankingTempoNomeZero = nome_completo;
        }

        if (RankingTempoUm < quantidadeMesesBeneficio && RankingTempoZero > quantidadeMesesBeneficio || RankingTempoUm == 0 || RankingTempoUm == RankingTempoZero) {

            RankingTempoUm = quantidadeMesesBeneficio;
            RankingTempoNomeUm = nome_completo;
        }

        
         
         System.out.println("\nTotal de benefici�rios: " + quantidadeBeneficiario);
         System.out.println("Total de valor que ser� concedido: " + valorTotalBeneficio);
         
         System.out.println("Primeiro maior valor do benef�cio: " + RankingBeneficioNomeZero);
         System.out.println("Segundo maior valor do benef�cio: " + RankingBeneficioNomeUm);
         
         System.out.println("Primeiro maior tempo que ir� receber os benef�cio: " + RankingTempoNomeZero);
         System.out.println("Segundo maior tempo que ir� receber os benef�cio: " + RankingTempoNomeUm);
 		
 		System.out.println("\nVoc� saiu. Obrigado por usar nosso sistema!\n");
        

	}
    

	public static double empregador(String categoria, double numeroDeFuncionarios, double valorBeneficio) {
		double beneficio = 200;
		double valorEmpregador;
		double valorMeses = 0;

		if (categoria.equalsIgnoreCase("Empregador")) {
			valorEmpregador = numeroDeFuncionarios * beneficio;
			valorMeses = valorEmpregador * mesesBeneficioEmpregadores(categoria);

		}
		return valorMeses;
	}

	/*
	 * Neste ponto havera as Regras de Neg�cio Espec�ficas
	 * 
	 * Letras = A. - O benef�cio s� ser� concedido para maiores de 18 anos;
	 */

	public static boolean verificaMaioridade(String nome_completo, int idade) {
		if (idade < 18) {
			System.out.println("Ol� " + nome_completo);
			System.out.println("Voc� n�o � maior de 18, logo n�o tem direito ao aux�lio");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Letra = L - O benef�cio ser� de 6 meses para desempregados;
	 */
	public static void desempregado(String categoria, double valorAuxilio) {
		if (categoria.equalsIgnoreCase("Desempregado")) {
			System.out.println("O seu benef�cio ser� de 6 meses no valor total de R$ " + (valorAuxilio * 6));
		}
	}

	/*
	 * Letra = M - O benef�cio de pessoas que moram em S�o Paulo ter� acr�scimo de
	 * 10%;
	 */

	public static double beneficioUFSP(double valor, String UF) {
		double valorSP = 0.10;

		if (UF.equalsIgnoreCase("SP")) {
			return (valor * valorSP);
		} else {
			return valor;
		}

	}

	/*
	 * Letra = N - O benef�cio de pessoas que moram em Santa Catarina ter� acr�scimo
	 * de 5%;
	 */
	public static double beneficioUFSC(double valor, String UF) {
		double valorSC = 0.05;

		if (UF.equalsIgnoreCase("SC")) {
			return (valor * valorSC);
		} else {
			return valor;
		}
	}

	/*
	 * Letra = I. Foi passado pelo professor Arquelau que poderia estar usando a
	 * letra J ou a segunda letra do meu sobrenome
	 * 
	 * Letra = O - Benef�cio ser� de 7 meses para empregadores;
	 */

	public static int mesesBeneficioEmpregadores(String categoria) {

		int mesesBeneficioEmpregadores = 7;
		boolean dentroDoRange = false;

		if (categoria.equals("Empregador")) {

			mesesBeneficioEmpregadores = 7;

		} else {

			do {

				System.out.println("Quantidade de meses do benefic�o \r" + "Min�mo de 2 meses, e max�mo de 12 meses");
				mesesBeneficioEmpregadores = recebeValorInteiro();

				if (mesesBeneficioEmpregadores >= 2)
					if (mesesBeneficioEmpregadores <= 12)
						dentroDoRange = true;

			} while (!dentroDoRange);

		}

		return mesesBeneficioEmpregadores;

	}

	/*
	 * Letra = W - O benef�cio de pessoas que moram em Minas Gerais ter� acr�scimo
	 * de 8%;
	 */
	public static double beneficioUFMG(double valor, String UF) {

		double valorMG = 0.08;

		if (UF.equalsIgnoreCase("MG"))
			return (valor * valorMG);
		else
			return valor;

	}

	/*
	 * Metodo que faz o calculo do beneficio
	 */

	public static double calculoAuxilioBase(String categoria, int quantidadeFuncionario, boolean desempregado) {

		Scanner teclado = new Scanner(System.in);

		double beneficio = 0.00D;
		double valorUser;
		double valor = 200.00D;

		if (categoria.equalsIgnoreCase("Desempregado")) {

			System.out.print("Para desempregados, informe um valor do benef�cio entre R$ 1.500 e R$ 2.300: ");
			valorUser = recebeValorDouble();
			boolean sair = true;
			while (sair == true) {

				if (valorUser >= 1500.00D && valorUser <= 2300.00D) {
					beneficio = valorUser;
					sair = false;
				} else {
					System.out.println("Informe um valor entre R$ 1.500 e R$ 2.300!");
					valorUser = recebeValorDouble();
					sair = true;
				}

			}
		} else if (categoria.equalsIgnoreCase("Empregador")) {
			beneficio = valor * quantidadeFuncionario;

		} else if (categoria.equalsIgnoreCase("Empregado")) {
			System.out.print("Para empregados, informe um valor do benef�cio entre R$ 1.000 e R$ 1.800: ");
			valorUser = recebeValorDouble();

			boolean sair = true;
			while (sair == true) {

				if (valorUser >= 1000 && valorUser <= 1800) {
					beneficio = valorUser;
					sair = false;
				} else {
					System.out.println("Informe um valor entre R$ 1.000 e R$ 1.800!");
					valorUser = recebeValorDouble();
					sair = true;
				}
			}

		} else {
			beneficio = 0.00D;
		}
		return beneficio;

	}

	public static String recebeTexto() {
		Scanner teclado = new Scanner(System.in);
		String entradaTexto = teclado.nextLine();

		return entradaTexto;
	}

	public static int recebeValorInteiro() {
		Scanner teclado = new Scanner(System.in);
		int numeroInteiro = teclado.nextInt();

		return numeroInteiro;
	}

	public static double recebeValorDouble() {
		Scanner teclado = new Scanner(System.in);
		double numeroDouble = teclado.nextDouble();

		return numeroDouble;
	}

	/*
	 * Metodo que faz o calculo da idade do beneficiario
	 */
	public static int calculaIdade(Date dataNasc) {

		Calendar nascimento = new GregorianCalendar();

		nascimento.setTime(dataNasc);

		// Cria um objeto calendar com a data atual
		Calendar dataHoje = Calendar.getInstance();

		// Obt�m a idade baseado no ano
		int idadeAno = dataHoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);

		nascimento.add(Calendar.YEAR, idadeAno);

		// Se a data de hoje � antes da data de Nascimento, ent�o diminui 1
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

	/*
	 * Valida a UF
	 */
	public static boolean validaUf(String UF) {
		String[] aUF = {
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

		return Arrays.asList(aUF).contains(UF);

	}
}
