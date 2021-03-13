package mytry.com.oocourse.spec1.main;

import mytry.com.oocourse.spec1.exceptions.EqualPersonIdException;
import mytry.com.oocourse.spec1.exceptions.EqualRelationException;
import mytry.com.oocourse.spec1.exceptions.PersonIdNotFoundException;
import mytry.com.oocourse.spec1.exceptions.RelationNotFoundException;

import java.math.BigInteger;
import java.util.ArrayList;

public class MyNetwork implements Network {
    private ArrayList<Person> people;
    private boolean find;

    public MyNetwork() {
        people = new ArrayList<>();
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Person getPerson(int id) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                return people.get(i);
            }
        }
        return null;
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        if (contains(person.getId())) {
            throw new EqualPersonIdException();
        } else {
            people.add(person);
        }
    }

    @Override
    public void addRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualRelationException {
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        if (p1 == null || p2 == null) {
            throw new PersonIdNotFoundException();
        } else if ((id1 != id2) && p1.isLinked(p2)) {
            throw new EqualRelationException();
        } else if (id1 == id2) {
            return;
        } else {
            p1.link(p2, value);
            p2.link(p1, value);
        }
    }

    @Override
    public int queryValue(int id1, int id2)
            throws PersonIdNotFoundException, RelationNotFoundException {
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        if (p1 == null || p2 == null) {
            throw new PersonIdNotFoundException();
        } else if (!p1.isLinked(p2)) {
            throw new RelationNotFoundException();
        } else {
            return p1.queryValue(p2);
        }
    }

    @Override
    public BigInteger queryConflict(int id1, int id2) throws PersonIdNotFoundException {
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        if (p1 == null || p2 == null) {
            throw new PersonIdNotFoundException();
        } else {
            return p1.getCharacter().xor(p2.getCharacter());
        }
    }

    @Override
    public int queryAcquaintanceSum(int id) throws PersonIdNotFoundException {
        MyPerson p = (MyPerson) getPerson(id);
        if (p == null) {
            throw new PersonIdNotFoundException();
        } else {
            return p.getAcquaintanceSum();
        }
    }

    @Override
    public int compareAge(int id1, int id2) throws PersonIdNotFoundException {
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        if (p1 == null || p2 == null) {
            throw new PersonIdNotFoundException();
        } else {
            return p1.getAge() - p2.getAge();
        }
    }

    @Override
    public int compareName(int id1, int id2) throws PersonIdNotFoundException {
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        if (p1 == null || p2 == null) {
            throw new PersonIdNotFoundException();
        } else {
            return p1.getName().compareTo(p2.getName());
        }
    }

    @Override
    public int queryPeopleSum() {
        return people.size();
    }

    @Override
    public int queryNameRank(int id) throws PersonIdNotFoundException {
        if (!contains(id)) {
            throw new PersonIdNotFoundException();
        } else {
            int sum = 1;
            for (int i = 0; i < people.size(); i++) {
                if (compareName(id, people.get(i).getId()) > 0) {
                    sum++;
                }
            }
            return sum;
        }
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        MyPerson p1 = (MyPerson) getPerson(id1);
        MyPerson p2 = (MyPerson) getPerson(id2);
        if (p1 == null || p2 == null) {
            throw new PersonIdNotFoundException();
        } else {
            ArrayList<Person> visit = new ArrayList<>();
            find = false;
            if (id1 == id2) {
                return true;
            }
            bfs(visit, p1, p2);
            return find;
        }
    }

    private void bfs(ArrayList<Person> visit, Person p1, Person p2) {
        ArrayList<Person> q = new ArrayList<>();
        visit.add(p1);
        q.add(p1);
        while (!q.isEmpty()) {
            ArrayList<Person> acq = ((MyPerson) q.get(0)).getAcquaintance();
            q.remove(0);
            for (int i = 0; i < acq.size(); i++) {
                if (visit.contains(acq.get(i))) {
                    continue;
                }
                if (acq.get(i).equals(p2)) {
                    find = true;
                    q.clear();
                    break;
                }
                visit.add(acq.get(i));
                q.add(acq.get(i));
            }
        }
    }
}
