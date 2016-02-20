package org.revo.service.impl

import com.paypal.api.payments.*
import org.springframework.core.env.Environment
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException

import javax.servlet.http.HttpServletRequest

/**
 * Created by revo on 18/01/16.
 */
class Util {
    static boolean check(PropertyValue p, List<String> ignoreNames, List<String> ignorePackages) {
        boolean b = (!ignorePackages.any { p.type.name.startsWith(it) } || p.type.enum)
        if (b) {
            b = !ignoreNames.contains(p.name)
        }
        b
    }

    static def CloneObject(def ob1, def ob2, List<String> ignoreNames, List<String> ignorePackages) {
        println "only check"
        ob2.metaPropertyValues.findAll {
            check(it, ignoreNames, ignorePackages)
        }.each {
            print(it.name + "  ")
            if (it.value) {
                ob1.setProperty(it.name, it.value)
            }
        }
        ob1
    }

    static def CloneObject(def ob1, def ob2, List<String> ignoreNames) {
        ignoreNames.add("class")
        CloneObject(ob1, ob2, ignoreNames, ["org.revo.domain.", "java.util."])
    }

    static Date AddToDate(Date date, int type, int step) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(type, step);
        cal.time
    }

    static List<SimpleGrantedAuthority> getRoles(Date lastPayment, String ROLE_TYPE) {
        List<SimpleGrantedAuthority> roles = [new SimpleGrantedAuthority("ROLE_AUTHENTICATED")]
        if (new Date().time > AddToDate(lastPayment, Calendar.YEAR, 1).time) {
            if (ROLE_TYPE.equals("ROLE_ADMIN"))
                roles << new SimpleGrantedAuthority("ROLE_SETTINGS")
            else
                throw new UsernameNotFoundException("your admin did't pay for this Year")
        } else {
            if (ROLE_TYPE.equals("ROLE_ADMIN")) {
                roles << new SimpleGrantedAuthority("ROLE_ADMIN")
                roles << new SimpleGrantedAuthority("ROLE_SETTINGS")
            } else roles << new SimpleGrantedAuthority("ROLE_STUDENT")
        }
        roles
    }

    static List<String> getRoles(Collection<GrantedAuthority> authorities) {
        authorities.collect {
            it.authority
        }
    }

    static Payment DefaultPayment(String name, int Plane, int cost, String Currency, int tax, int oldPlane, int shipping) {
        int OLdsubTotal = oldPlane * cost;
        int subtotal = Plane * cost - OLdsubTotal
        Details details = new Details();
        details.setShipping(String.valueOf(shipping));
        details.setTax(String.valueOf(tax))
        details.setSubtotal(String.valueOf(subtotal));
        Amount amount = new Amount();
        amount.setCurrency(Currency);
        amount.setTotal(String.valueOf(subtotal + shipping + tax));
        amount.setDetails(details);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("This is the payment for CreditHour");
        Item item = new Item();
        item.setName(name).setQuantity(String.valueOf(Plane)).setCurrency(Currency).setPrice(String.valueOf(cost));
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment
    }

    static RedirectUrls GETRedirectUrls(HttpServletRequest request, String uuid, Environment env) {
        RedirectUrls redirectUrls = new RedirectUrls();
        String ss = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.contextPath
        redirectUrls.setCancelUrl(ss + env.getProperty("paypal.failUrl") + "?uuid=" + uuid);
        redirectUrls.setReturnUrl(ss + env.getProperty("paypal.successUrl") + "?uuid=" + uuid);
        redirectUrls
    }

}
